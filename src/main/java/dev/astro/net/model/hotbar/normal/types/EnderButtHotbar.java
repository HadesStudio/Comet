package dev.astro.net.model.hotbar.normal.types;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.hotbar.normal.NormalHotbar;
import dev.astro.net.utilities.PlayerUtil;
import fr.mrmicky.fastparticles.ParticleType;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class EnderButtHotbar extends NormalHotbar {
    private final boolean mount;

    public EnderButtHotbar(String name, boolean mount) {
        super(name);
        this.mount = mount;
    }

    @Override
    public void onItemInteract(Player player) {
        PlayerUtil.playSound(player, "ORB_PICKUP");
        if (!mount) {
            player.setVelocity(player.getLocation().getDirection().normalize().setY(2.0));
            player.setVelocity(player.getLocation().getDirection().normalize().multiply(2F));
            return;
        }
        EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
        enderPearl.setVelocity(enderPearl.getVelocity().multiply(1.3));
        enderPearl.setPassenger(player);
        enderPearl.setBounce(false);
        player.updateInventory();
        ParticleType particleType = ParticleType.of("VILLAGER_HAPPY");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || enderPearl.getPassenger() == null || enderPearl.isOnGround()|| enderPearl.isDead() || !enderPearl.isValid()) {
                    enderPearl.remove();
                    cancel();
                }
                particleType.spawn(enderPearl.getWorld(), enderPearl.getLocation(), 1);
            }
        }.runTaskTimer(CometPlugin.getPlugin(), 1L, 1L);
    }
}