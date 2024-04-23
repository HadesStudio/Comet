package dev.astro.net.model.cosmetics.impl.balloons.listener;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.balloons.BalloonManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

@RequiredArgsConstructor
public class BalloonListener implements Listener {

    private final Comet plugin;
    private final BalloonManager balloonManager;

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (balloonManager.isBalloon(event.getRightClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (balloonManager.isBalloon(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (balloonManager.isBalloon(event.getEntity())) {
            event.setCancelled(true);
        }
    }
}
