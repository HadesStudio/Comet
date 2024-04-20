package dev.rage.comet.model.hotbar.normal.types;

import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import dev.rage.comet.model.hubselector.menu.HubSelectorMenu;
import dev.rage.comet.utilities.Comet;
import org.bukkit.entity.Player;


public class HubSelectorHotbar extends NormalHotbar {

    private final Comet plugin;

    public HubSelectorHotbar(String name, Comet plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void onItemInteract(Player player) {
        new HubSelectorMenu(plugin.getHubSelectorManager()).openMenu(player);
    }
}
