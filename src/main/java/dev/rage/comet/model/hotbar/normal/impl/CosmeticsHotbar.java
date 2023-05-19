package dev.rage.comet.model.hotbar.normal.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.menu.CosmeticsMenu;
import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import dev.rage.comet.model.selector.server.menu.ServerSelectorMenu;
import org.bukkit.entity.Player;

public class CosmeticsHotbar extends NormalHotbar {

    public CosmeticsHotbar(String name) {
        super(name);
    }

    @Override
    public void onItemInteract(Player player) {
        new CosmeticsMenu().openMenu(player);
    }
}
