package dev.rage.comet.model.hotbar.normal.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.menu.CosmeticsMenu;
import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import org.bukkit.entity.Player;

public class CosmeticsHotbar extends NormalHotbar {

    private final Comet plugin;

    public CosmeticsHotbar(String name, Comet plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void onItemInteract(Player player) {
        new CosmeticsMenu(plugin).openMenu(player);
    }
}
