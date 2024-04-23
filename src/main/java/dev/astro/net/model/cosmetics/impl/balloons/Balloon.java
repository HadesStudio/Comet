package dev.astro.net.model.cosmetics.impl.balloons;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

@Getter
@RequiredArgsConstructor
public class Balloon {

    private final Comet plugin;
    private final Player player;
    private final ItemStack head;
    private ArmorStand armorStandLow, armorStandHigh;
    private Bat bat;
    private int ticks = 0;

    public void spawn() {
        Location location = getCalcAngleLoc();

        this.armorStandLow = player.getWorld().spawn(location, ArmorStand.class);
        armorStandLow.setGravity(false);
        armorStandLow.setVisible(false);
        armorStandLow.setCustomNameVisible(false);
        armorStandLow.setSmall(true);
        armorStandLow.setMetadata(CometPlugin.getPlugin().getName(), new FixedMetadataValue(CometPlugin.getPlugin(), true));

        this.armorStandHigh = player.getWorld().spawn(location.add(0, 2, 0), ArmorStand.class);
        armorStandHigh.setGravity(false);
        armorStandHigh.setVisible(false);
        armorStandHigh.setCustomNameVisible(false);
        armorStandHigh.setSmall(true);
        armorStandHigh.setHelmet(head);
        armorStandHigh.setMetadata(CometPlugin.getPlugin().getName(), new FixedMetadataValue(CometPlugin.getPlugin(), true));

        this.bat = player.getWorld().spawn(location, Bat.class);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        bat.setMetadata(CometPlugin.getPlugin().getName(), new FixedMetadataValue(CometPlugin.getPlugin(), true));

        armorStandHigh.setPassenger(bat);

        silent(bat);
        noAI(bat);
        invincible(bat);

        bat.setLeashHolder(player);
    }

    public void remove() {
        bat.setLeashHolder(null);
        bat.remove();
        armorStandLow.remove();
        armorStandHigh.remove();
    }

    public boolean tick() {
        if (player == null || armorStandLow == null || armorStandLow.isDead() || armorStandHigh == null || armorStandHigh.isDead()
                || bat == null || bat.isDead()/* || !player.isLeashed()*/) return false;

        moveArmorStand();
        updateHeadAngle();

        return true;
    }

    private void moveArmorStand() {
        Location newLoc = getCalcAngleLoc();

        if (newLoc.getWorld() != armorStandLow.getWorld()) {
            CometPlugin.getPlugin().getServer().getScheduler().runTask(CometPlugin.getPlugin(), () -> {
                armorStandLow.teleport(newLoc);
                bat.teleport(newLoc.add(0, 2, 0));
                armorStandHigh.teleport(newLoc);
            });
        } else {
            armorStandLow.teleport(newLoc);
            bat.teleport(newLoc.add(0, 2, 0));
            armorStandHigh.teleport(newLoc);
        }
    }

    private void updateHeadAngle() {
        ticks++;

        if (ticks == 36)
            ticks = 0;

        EulerAngle angle = new EulerAngle(0, Math.toRadians(ticks * 10), 0);
        armorStandHigh.setHeadPose(angle);
    }

    private Location getCalcAngleLoc() {
        Location loc = player.getLocation();
        Vector vector = player.getLocation().getDirection().normalize();

        double angle = Math.toRadians(270);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double newX = loc.getX() + (vector.getX() * cos) + (vector.getZ() * sin);
        double newZ = loc.getZ() + (vector.getZ() * cos) - (vector.getX() * sin);

        return new Location(loc.getWorld(), newX, loc.getY(), newZ, loc.getYaw(), loc.getPitch());
    }

    private void silent(Entity bukkitEntity) {
        NBTEntity nbtEntity = new NBTEntity(bukkitEntity);
        nbtEntity.setByte("Silent", (byte) 1);
    }

    private void noAI(Entity bukkitEntity) {
        NBTEntity nbtEntity = new NBTEntity(bukkitEntity);
        nbtEntity.setInteger("NoAI", 1);
    }

    private void invincible(Entity bukkitEntity) {
        NBTEntity nbtEntity = new NBTEntity(bukkitEntity);
        nbtEntity.setByte("Invulnerable", (byte) 1);
    }
}