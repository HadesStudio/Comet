package dev.rage.comet.utilities.compatible;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public enum Particle {
    EXPLOSION_NORMAL("explode", 0, -1, ParticleProperty.DIRECTIONAL),
    EXPLOSION_LARGE("largeexplode", 1, -1),
    EXPLOSION_HUGE("hugeexplosion", 2, -1),
    FIREWORKS_SPARK("fireworksSpark", 3, -1, ParticleProperty.DIRECTIONAL),
    WATER_BUBBLE("bubble", 4, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),
    WATER_SPLASH("splash", 5, -1, ParticleProperty.DIRECTIONAL),
    WATER_WAKE("wake", 6, 7, ParticleProperty.DIRECTIONAL),
    SUSPENDED("suspended", 7, -1, ParticleProperty.REQUIRES_WATER),
    SUSPENDED_DEPTH("depthSuspend", 8, -1, ParticleProperty.DIRECTIONAL),
    CRIT("crit", 9, -1, ParticleProperty.DIRECTIONAL),
    CRIT_MAGIC("magicCrit", 10, -1, ParticleProperty.DIRECTIONAL),
    SMOKE_NORMAL("smoke", 11, -1, ParticleProperty.DIRECTIONAL),
    SMOKE_LARGE("largesmoke", 12, -1, ParticleProperty.DIRECTIONAL),
    SPELL("spell", 13, -1),
    SPELL_INSTANT("instantSpell", 14, -1),
    SPELL_MOB("mobSpell", 15, -1, ParticleProperty.COLORABLE),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1, ParticleProperty.COLORABLE),
    SPELL_WITCH("witchMagic", 17, -1),
    DRIP_WATER("dripWater", 18, -1),
    DRIP_LAVA("dripLava", 19, -1),
    VILLAGER_ANGRY("angryVillager", 20, -1),
    VILLAGER_HAPPY("happyVillager", 21, -1, ParticleProperty.DIRECTIONAL),
    TOWN_AURA("townaura", 22, -1, ParticleProperty.DIRECTIONAL),
    NOTE("note", 23, -1, ParticleProperty.COLORABLE),
    PORTAL("portal", 24, -1, ParticleProperty.DIRECTIONAL),
    ENCHANTMENT_TABLE("enchantmenttable", 25, -1, ParticleProperty.DIRECTIONAL),
    FLAME("flame", 26, -1, ParticleProperty.DIRECTIONAL),
    LAVA("lava", 27, -1),
    FOOTSTEP("footstep", 28, -1),
    CLOUD("cloud", 29, -1, ParticleProperty.DIRECTIONAL),
    REDSTONE("reddust", 30, -1, ParticleProperty.COLORABLE),
    SNOWBALL("snowballpoof", 31, -1),
    SNOW_SHOVEL("snowshovel", 32, -1, ParticleProperty.DIRECTIONAL),
    SLIME("slime", 33, -1),
    HEART("heart", 34, -1),
    BARRIER("barrier", 35, 8),
    ITEM_CRACK("iconcrack", 36, -1, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    BLOCK_CRACK("blockcrack", 37, -1, ParticleProperty.REQUIRES_DATA),
    BLOCK_DUST("blockdust", 38, 7, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    WATER_DROP("droplet", 39, 8),
    ITEM_TAKE("take", 40, 8),
    MOB_APPEARANCE("mobappearance", 41, 8);

    private static final Map<String, Particle> NAME_MAP = Maps.newHashMap();

    private static final Map<Integer, Particle> ID_MAP = Maps.newHashMap();

    private final String name;

    private final int id;

    private final int requiredVersion;

    private final List<ParticleProperty> properties;

    Particle(String name, int id, int requiredVersion, ParticleProperty... properties) {
        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.properties = Arrays.asList(properties);
    }

    static {
        for (Particle effect : values()) {
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
        }
    }
    public boolean hasProperty(ParticleProperty property) {
        return this.properties.contains(property);
    }

    public boolean isSupported() {
        if (this.requiredVersion == -1)
            return true;
        return (ParticlePacket.getVersion() >= this.requiredVersion);
    }

    public static Particle fromName(String name) {
        for (Map.Entry<String, Particle> entry : NAME_MAP.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(name))
                continue;
            return entry.getValue();
        }
        return null;
    }

    public static Particle fromId(int id) {
        for (Map.Entry<Integer, Particle> entry : ID_MAP.entrySet()) {
            if (entry.getKey() != id)
                continue;
            return entry.getValue();
        }
        return null;
    }

    private static boolean isWater(Location location) {
        Material material = location.getBlock().getType();
        return (material == Material.WATER || material == Material.STATIONARY_WATER);
    }

    private static boolean isLongDistance(Location location, List<Player> players) {
        String world = location.getWorld().getName();
        for (Player player : players) {
            Location playerLocation = player.getLocation();
            if (!world.equals(playerLocation.getWorld().getName()) || playerLocation.distanceSquared(location) < 65536.0D)
                continue;
            return true;
        }
        return false;
    }

    private static boolean isDataCorrect(Particle effect, ParticleData data) {
        return (((effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof BlockData) || (effect == ITEM_CRACK && data instanceof ItemData));
    }

    private static boolean isColorCorrect(Particle effect, ParticleColor color) {
        return (((effect == SPELL_MOB || effect == SPELL_MOB_AMBIENT || effect == REDSTONE) && color instanceof OrdinaryColor) || (effect == NOTE && color instanceof NoteColor));
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect requires additional data");
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw new IllegalArgumentException("There is no water at the center location");
        (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, (range > 256.0D), null)).sendTo(center, range);
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect requires additional data");
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw new IllegalArgumentException("There is no water at the center location");
        (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null)).sendTo(center, players);
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect requires additional data");
        if (!hasProperty(ParticleProperty.DIRECTIONAL))
            throw new IllegalArgumentException("This particle effect is not directional");
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw new IllegalArgumentException("There is no water at the center location");
        (new ParticlePacket(this, direction, speed, (range > 256.0D), null)).sendTo(center, range);
    }

    public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect requires additional data");
        if (!hasProperty(ParticleProperty.DIRECTIONAL))
            throw new IllegalArgumentException("This particle effect is not directional");
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center))
            throw new IllegalArgumentException("There is no water at the center location");
        (new ParticlePacket(this, direction, speed, isLongDistance(center, players), null)).sendTo(center, players);
    }

    public void display(Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        display(direction, speed, center, Arrays.asList(players));
    }

    public void display(ParticleColor color, Location center, double range) throws ParticleVersionException, ParticleColorException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (!hasProperty(ParticleProperty.COLORABLE))
            throw new ParticleColorException("This particle effect is not colorable");
        if (!isColorCorrect(this, color))
            throw new ParticleColorException("The particle color type is incorrect");
        (new ParticlePacket(this, color, (range > 256.0D))).sendTo(center, range);
    }

    public void display(ParticleColor color, Location center, List<Player> players) throws ParticleVersionException, ParticleColorException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (!hasProperty(ParticleProperty.COLORABLE))
            throw new ParticleColorException("This particle effect is not colorable");
        if (!isColorCorrect(this, color))
            throw new ParticleColorException("The particle color type is incorrect");
        (new ParticlePacket(this, color, isLongDistance(center, players))).sendTo(center, players);
    }

    public void display(ParticleColor color, Location center, Player... players) throws ParticleVersionException, ParticleColorException {
        display(color, center, Arrays.asList(players));
    }

    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleVersionException, ParticleDataException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect does not require additional data");
        if (!isDataCorrect(this, data))
            throw new ParticleDataException("The particle data type is incorrect");
        (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, (range > 256.0D), data)).sendTo(center, range);
    }

    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect does not require additional data");
        if (!isDataCorrect(this, data))
            throw new ParticleDataException("The particle data type is incorrect");
        (new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data)).sendTo(center, players);
    }

    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
        display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleVersionException, ParticleDataException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect does not require additional data");
        if (!isDataCorrect(this, data))
            throw new ParticleDataException("The particle data type is incorrect");
        (new ParticlePacket(this, direction, speed, (range > 256.0D), data)).sendTo(center, range);
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
        if (!isSupported())
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        if (!hasProperty(ParticleProperty.REQUIRES_DATA))
            throw new ParticleDataException("This particle effect does not require additional data");
        if (!isDataCorrect(this, data))
            throw new ParticleDataException("The particle data type is incorrect");
        (new ParticlePacket(this, direction, speed, isLongDistance(center, players), data)).sendTo(center, players);
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleVersionException, ParticleDataException {
        display(data, direction, speed, center, Arrays.asList(players));
    }

    public enum ParticleProperty {
        REQUIRES_WATER, REQUIRES_DATA, DIRECTIONAL, COLORABLE
    }

    public static abstract class ParticleData {
        private final Material material;

        private final byte data;

        private final int[] packetData;

        public ParticleData(Material material, byte data) {
            this.material = material;
            this.data = data;
            this.packetData = new int[] { material.getId(), data };
        }

        public Material getMaterial() {
            return this.material;
        }

        public byte getData() {
            return this.data;
        }

        public int[] getPacketData() {
            return this.packetData;
        }

        public String getPacketDataString() {
            return "_" + this.packetData[0] + "_" + this.packetData[1];
        }
    }

    public static final class ItemData extends ParticleData {
        public ItemData(Material material, byte data) {
            super(material, data);
        }
    }

    public static final class BlockData extends ParticleData {
        public BlockData(Material material, byte data) throws IllegalArgumentException {
            super(material, data);
            if (!material.isBlock())
                throw new IllegalArgumentException("The material is not a block");
        }
    }

    public static abstract class ParticleColor {
        public abstract float getValueX();

        public abstract float getValueY();

        public abstract float getValueZ();
    }

    public static final class OrdinaryColor extends ParticleColor {
        private final int red;

        private final int green;

        private final int blue;

        public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
            if (red < 0)
                throw new IllegalArgumentException("The red value is lower than 0");
            if (red > 255)
                throw new IllegalArgumentException("The red value is higher than 255");
            this.red = red;
            if (green < 0)
                throw new IllegalArgumentException("The green value is lower than 0");
            if (green > 255)
                throw new IllegalArgumentException("The green value is higher than 255");
            this.green = green;
            if (blue < 0)
                throw new IllegalArgumentException("The blue value is lower than 0");
            if (blue > 255)
                throw new IllegalArgumentException("The blue value is higher than 255");
            this.blue = blue;
        }

        public OrdinaryColor(Color color) {
            this(color.getRed(), color.getGreen(), color.getBlue());
        }

        public int getRed() {
            return this.red;
        }

        public int getGreen() {
            return this.green;
        }

        public int getBlue() {
            return this.blue;
        }

        public float getValueX() {
            return this.red / 255.0F;
        }

        public float getValueY() {
            return this.green / 255.0F;
        }

        public float getValueZ() {
            return this.blue / 255.0F;
        }
    }

    public static final class NoteColor extends ParticleColor {
        private final int note;

        public NoteColor(int note) throws IllegalArgumentException {
            if (note < 0)
                throw new IllegalArgumentException("The note value is lower than 0");
            if (note > 24)
                throw new IllegalArgumentException("The note value is higher than 24");
            this.note = note;
        }

        public float getValueX() {
            return this.note / 24.0F;
        }

        public float getValueY() {
            return 0.0F;
        }

        public float getValueZ() {
            return 0.0F;
        }
    }

    private static final class ParticleDataException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleDataException(String message) {
            super(message);
        }
    }

    private static final class ParticleColorException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleColorException(String message) {
            super(message);
        }
    }

    private static final class ParticleVersionException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleVersionException(String message) {
            super(message);
        }
    }

    public static final class ParticlePacket {
        private static int version;

        private static Class<?> enumParticle;

        private static Constructor<?> packetConstructor;

        private static Method getHandle;

        private static Field playerConnection;

        private static Method sendPacket;

        private static boolean initialized;

        private final Particle effect;

        private float offsetX;

        private final float offsetY;

        private final float offsetZ;

        private final float speed;

        private final int amount;

        private final boolean longDistance;

        private final ParticleData data;

        private Object packet;

        public ParticlePacket(Particle effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleData data) throws IllegalArgumentException {
            initialize();
            if (speed < 0.0F)
                throw new IllegalArgumentException("The speed is lower than 0");
            if (amount < 0)
                throw new IllegalArgumentException("The amount is lower than 0");
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
            this.longDistance = longDistance;
            this.data = data;
        }

        public ParticlePacket(Particle effect, Vector direction, float speed, boolean longDistance, ParticleData data) throws IllegalArgumentException {
            this(effect, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, 0, longDistance, data);
        }

        public ParticlePacket(Particle effect, ParticleColor color, boolean longDistance) {
            this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1.0F, 0, longDistance, null);
            if (effect == Particle.REDSTONE && color instanceof OrdinaryColor && ((OrdinaryColor)color).getRed() == 0)
                this.offsetX = 1.17549435E-38F;
        }

        public static void initialize() throws VersionIncompatibleException {
            if (initialized)
                return;
            try {
                version = Integer.parseInt(Character.toString(ParticleReflection.PackageType.getServerVersion().charAt(3)));
                if (version > 7)
                    enumParticle = ParticleReflection.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                Class<?> packetClass = ParticleReflection.PackageType.MINECRAFT_SERVER.getClass((version < 7) ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
                packetConstructor = ParticleReflection.getConstructor(packetClass);
                getHandle = ParticleReflection.getMethod("CraftPlayer", ParticleReflection.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
                playerConnection = ParticleReflection.getField("EntityPlayer", ParticleReflection.PackageType.MINECRAFT_SERVER, false, "playerConnection");
                sendPacket = ParticleReflection.getMethod(playerConnection.getType(), "sendPacket", ParticleReflection.PackageType.MINECRAFT_SERVER.getClass("Packet"));
            } catch (Exception exception) {
                throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
            }
            initialized = true;
        }

        public static int getVersion() {
            if (!initialized)
                initialize();
            return version;
        }

        public static boolean isInitialized() {
            return initialized;
        }

        private void initializePacket(Location center) throws PacketInstantiationException {
            if (this.packet != null)
                return;
            try {
                this.packet = packetConstructor.newInstance();
                if (version < 8) {
                    String name = this.effect.getName();
                    if (this.data != null)
                        name = name + this.data.getPacketDataString();
                    ParticleReflection.setValue(this.packet, true, "a", name);
                } else {
                    ParticleReflection.setValue(this.packet, true, "a", enumParticle.getEnumConstants()[this.effect.getId()]);
                    ParticleReflection.setValue(this.packet, true, "j", Boolean.valueOf(this.longDistance));
                    if (this.data != null) {
                        int[] packetData = this.data.getPacketData();
                        (new int[1])[0] = packetData[0] | packetData[1] << 12;
                        ParticleReflection.setValue(this.packet, true, "k", (this.effect == Particle.ITEM_CRACK) ? packetData : new int[1]);
                    }
                }
                ParticleReflection.setValue(this.packet, true, "b", Float.valueOf((float)center.getX()));
                ParticleReflection.setValue(this.packet, true, "c", Float.valueOf((float)center.getY()));
                ParticleReflection.setValue(this.packet, true, "d", Float.valueOf((float)center.getZ()));
                ParticleReflection.setValue(this.packet, true, "e", this.offsetX);
                ParticleReflection.setValue(this.packet, true, "f", Float.valueOf(this.offsetY));
                ParticleReflection.setValue(this.packet, true, "g", Float.valueOf(this.offsetZ));
                ParticleReflection.setValue(this.packet, true, "h", Float.valueOf(this.speed));
                ParticleReflection.setValue(this.packet, true, "i", Integer.valueOf(this.amount));
            } catch (Exception exception) {
                throw new PacketInstantiationException("Packet instantiation failed", exception);
            }
        }

        public void sendTo(Location center, Player player) throws PacketInstantiationException, PacketSendingException {
            initializePacket(center);
            try {
                sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), this.packet);
            } catch (Exception exception) {
                throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", exception);
            }
        }

        public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
            if (players.isEmpty())
                throw new IllegalArgumentException("The player list is empty");
            for (Player player : players)
                sendTo(center, player);
        }

        public void sendTo(Location center, double range) throws IllegalArgumentException {
            if (range < 1.0D)
                throw new IllegalArgumentException("The range is lower than 1");
            String worldName = center.getWorld().getName();
            double squared = range * range;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) > squared)
                    continue;
                sendTo(center, player);
            }
        }

        private static final class VersionIncompatibleException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public VersionIncompatibleException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        private static final class PacketInstantiationException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketInstantiationException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        private static final class PacketSendingException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketSendingException(String message, Throwable cause) {
                super(message, cause);
            }
        }
    }
}