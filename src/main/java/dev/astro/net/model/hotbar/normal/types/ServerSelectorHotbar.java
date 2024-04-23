package dev.astro.net.model.hotbar.normal.types;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.hotbar.normal.NormalHotbar;
import dev.astro.net.model.selector.menu.ServerSelectorMenu;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class ServerSelectorHotbar extends NormalHotbar {

    private final Comet plugin;

    public ServerSelectorHotbar(Comet plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void onItemInteract(Player player) {
        ServerSelectorMenu serverSelectorMenu = new ServerSelectorMenu(plugin);
        serverSelectorMenu.openMenu(player);
    }
}
