package dev.risas.countach.listeners.hotbar;

import dev.risas.countach.Countach;
import dev.risas.countach.model.hotbar.normal.NormalHotbar;
import dev.risas.countach.model.hotbar.normal.NormalHotbarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Risas
 * Project: Countach
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class NormalHotbarListener implements Listener {

    private final NormalHotbarManager normalHotbarManager;

    public NormalHotbarListener(Countach plugin) {
        this.normalHotbarManager = plugin.getNormalHotbarManager();
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        normalHotbarManager.giveHotbar(event.getPlayer());
    }

    @EventHandler
    private void onHotbarInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;

        NormalHotbar hotbar = normalHotbarManager.getHotbar(event.getItem());

        if (hotbar == null) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        hotbar.onItemInteract(player);
        player.updateInventory();
    }
}
