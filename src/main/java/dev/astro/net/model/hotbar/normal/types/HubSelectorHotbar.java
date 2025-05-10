package dev.astro.net.model.hotbar.normal.types;

import dev.astro.net.model.hotbar.normal.NormalHotbar;
import dev.astro.net.model.hubselector.menu.HubSelectorMenu;
import dev.astro.net.utilities.Comet;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;


public class HubSelectorHotbar extends NormalHotbar {

    private final Comet plugin;

    public HubSelectorHotbar(String name, Comet plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void onItemInteract(Player player, PlayerInteractEvent playerInteractEvent) {
        new HubSelectorMenu(plugin.getHubSelectorManager()).openMenu(player);
    }
}
