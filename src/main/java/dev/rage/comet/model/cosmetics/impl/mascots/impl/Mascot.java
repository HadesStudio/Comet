package dev.rage.comet.model.cosmetics.impl.mascots.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.utilities.ChatUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nullable;

@Getter
public class Mascot {

    private final Comet plugin;

    private final Player player;
    private final ArmorStand armorStand;

    private final boolean active = false;

    private final Body body;

    private int i = 0;
    private boolean up = true;

    public Mascot(Comet plugin, Player player, @Nullable Body body) {
        this.plugin = plugin;
        this.player = player;

        if (body != null) this.body = body;
        else this.body = plugin.getMascotManager().getDefaultBody();

        this.armorStand = player.getWorld().spawn(calcLoc(player), ArmorStand.class);

        armorStand.setMetadata(plugin.getName(), new FixedMetadataValue(plugin, true));

        armorStand.setCustomNameVisible(true);

        if (body != null)
            armorStand.setCustomName(ChatUtil.translate(body.getDisplayName()));

        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);

        this.body.apply(this);
    }

    public boolean tick() {
        if (armorStand.isDead()) {
            die();
            return false;
        }

        // Max of ticks
        if (i == 5) {
            up = false;
        } else {
            if (i == -5) {
                up = true;
            }
        }

        if (up) {
            i++;
        } else {
            i--;
        }

        armorStand.teleport(calcLoc(player));
        updateHeadPose();
        if (body.isParticle() && body.getParticleType() != null)
            playParticles();

        return true;
    }

    private Location calcLoc(Player player) {
        Location location = player.getLocation();
        double x = location.getX(), y = location.getY() + 0.5, z = location.getZ();
        float yaw = location.getYaw();

        double radians = Math.toRadians(yaw), xDirection = -Math.sin(radians), zDirection = Math.cos(radians);
        double teleportX = x + (xDirection * -1), teleportZ = z + (zDirection * -1);

        // Calculate 'Y' movement for more smooth
        double teleportY = y + (i * 0.075);
        return new Location(location.getWorld(), teleportX, teleportY, teleportZ, yaw, 0);
    }

    private void playParticles() {
        Location location = armorStand.getLocation().add(0, 0.3, 0);

        body.getParticleType().spawn(location.getWorld(), location, 1, 0, 0, 0, 0, 1);
    }

    private void updateHeadPose() {
        if (armorStand != null) {
            armorStand.setHeadPose(armorStand.getHeadPose().setX(i * 0.05)); // Modify this decinal to change angle of head movement
        }
    }

    public void die() {
        armorStand.remove();
        plugin.getMascotManager().getMascots().remove(player.getUniqueId());
    }

    public void onDisable() {
        armorStand.remove();
    }
}
