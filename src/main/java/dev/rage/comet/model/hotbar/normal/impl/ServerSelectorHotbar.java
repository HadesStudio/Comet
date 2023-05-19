package dev.rage.comet.model.hotbar.normal.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import dev.rage.comet.model.selector.menu.ServerSelectorMenu;
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
