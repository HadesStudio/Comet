package dev.astro.net.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(!event.getPlayer().hasMetadata("comet-build-mode"));
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(!event.getPlayer().hasMetadata("comet-build-mode"));
    }

    @EventHandler
    private void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onExplosion(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            event.setCancelled(true);
        }
    }
}
