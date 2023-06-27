package dev.rage.comet.model.cosmetics.impl.balloons.listener;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class BalloonListener implements Listener {

    private final Comet plugin;
    private final BalloonManager balloonManager;

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (balloonManager.isBalloon(plugin, event.getRightClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (balloonManager.isBalloon(plugin, event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (balloonManager.isBalloon(plugin, event.getEntity())) {
            event.setCancelled(true);
        }
    }
}
