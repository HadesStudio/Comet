package dev.risas.countach.listeners;

import dev.risas.countach.Countach;
import dev.risas.countach.model.spawn.SpawnManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Risas
 * Project: Countach
 * Date: 14-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class SpawnListener implements Listener {

    private final SpawnManager spawnManager;

    public SpawnListener(Countach plugin) {
        this.spawnManager = plugin.getSpawnManager();
    }

    @EventHandler
    private void onJoinSpawn(PlayerJoinEvent event) {
        spawnManager.toSpawn(event.getPlayer());
    }

    @EventHandler
    private void onVoidSpawn(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                event.setCancelled(true);
                spawnManager.toSpawn(event.getEntity());
            }
        }
    }
}
