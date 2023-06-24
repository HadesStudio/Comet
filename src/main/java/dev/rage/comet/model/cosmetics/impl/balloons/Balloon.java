package dev.rage.comet.model.cosmetics.impl.balloons;

import dev.rage.comet.Comet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        Location location;

        try {
            location = getCalcAngleLoc().get();
        }
        catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.armorStandLow = player.getWorld().spawn(location, ArmorStand.class);
        armorStandLow.setGravity(false);
        armorStandLow.setVisible(false);
        armorStandLow.setCustomNameVisible(false);
        armorStandLow.setSmall(true);
        armorStandLow.setMetadata(plugin.getName(), new FixedMetadataValue(plugin, true));

        this.armorStandHigh = player.getWorld().spawn(location.add(0, 2, 0), ArmorStand.class);
        armorStandHigh.setGravity(false);
        armorStandHigh.setVisible(false);
        armorStandHigh.setCustomNameVisible(false);
        armorStandHigh.setSmall(true);
        armorStandHigh.setHelmet(head);
        armorStandHigh.setMetadata(plugin.getName(), new FixedMetadataValue(plugin, true));

        this.bat = player.getWorld().spawn(location, Bat.class);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        bat.setMetadata(plugin.getName(), new FixedMetadataValue(plugin, true));

        armorStandHigh.setPassenger(bat);

        silent(bat);
        noAI(bat);

        bat.setLeashHolder(player);
    }

    public void remove() {
        bat.setLeashHolder(null);
        bat.remove();
        armorStandLow.remove();
        armorStandHigh.remove();
    }

    public boolean tick() {
        if (armorStandLow == null || armorStandLow.isDead() || armorStandHigh == null || armorStandHigh.isDead()
                || bat == null || bat.isDead()) return false;

        moveArmorStand().join();
        updateHeadAngle().join();

        return true;
    }

    private CompletableFuture<Void> moveArmorStand() {
        return CompletableFuture.runAsync(() -> {
            Location newLoc;
            try {
                newLoc = getCalcAngleLoc().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            armorStandLow.teleport(newLoc);
            bat.teleport(newLoc.add(0, 2, 0));
            armorStandHigh.teleport(newLoc);
        });
    }

    private CompletableFuture<Void> updateHeadAngle() {
        return CompletableFuture.runAsync(() -> {
            ticks++;

            if (ticks == 36)
                ticks = 0;

            EulerAngle angle = new EulerAngle(0, Math.toRadians(ticks * 10), 0);
            armorStandHigh.setHeadPose(angle);
        });
    }

    private CompletableFuture<Location> getCalcAngleLoc() {
        return CompletableFuture.supplyAsync(() -> {
            Location loc = player.getLocation();
            Vector vector = player.getLocation().getDirection().normalize();

            double angle = Math.toRadians(270);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            double newX = loc.getX() + (vector.getX() * cos) + (vector.getZ() * sin);
            double newZ = loc.getZ() + (vector.getZ() * cos) - (vector.getX() * sin);

            return new Location(loc.getWorld(), newX, loc.getY(), newZ, loc.getYaw(), loc.getPitch());
        });
    }

    private void silent(Entity bukkitEntity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setByte("Silent", (byte) 1);
        nmsEntity.f(tag);
    }

    private void noAI(Entity bukkitEntity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
}
