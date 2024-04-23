package dev.astro.net.listeners.parkour;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.parkour.hotbar.ParkourHotbar;
import dev.astro.net.model.parkour.hotbar.ParkourHotbarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ParkourHotbarListener implements Listener {

    private final ParkourHotbarManager parkourHotbarManager;

    public ParkourHotbarListener(Comet plugin) {
        this.parkourHotbarManager = plugin.getParkourHotbarManager();
    }

    @EventHandler
    private void onHotbarInteract(PlayerInteractEvent event) {
        if (event.getItem() == null
                || event.getAction() != Action.RIGHT_CLICK_BLOCK
                && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        ParkourHotbar hotbar = parkourHotbarManager.getParkourHotbar(event.getItem());
        if (hotbar == null)
            return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        hotbar.onItemInteract(player);
    }
}
