package dev.rage.comet.model.cosmetics.impl.mascots.listener;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.mascots.MascotManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class MascotListener implements Listener {

    private final Comet plugin;
    private final MascotManager mascotManager;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (mascotManager.getMascots().containsKey(event.getPlayer().getUniqueId())) {
            mascotManager.getMascots().get(event.getPlayer().getUniqueId()).die();
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (mascotManager.getMascots().containsKey(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
