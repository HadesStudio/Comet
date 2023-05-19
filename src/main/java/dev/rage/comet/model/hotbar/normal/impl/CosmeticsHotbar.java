package dev.rage.comet.model.hotbar.normal.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.banners.BannersManager;
import dev.rage.comet.model.cosmetics.menu.CosmeticsMenu;
import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import org.bukkit.entity.Player;

public class CosmeticsHotbar extends NormalHotbar {

    private final BannersManager bannersManager;

    public CosmeticsHotbar(String name, BannersManager bannersManager) {
        super(name);
        this.bannersManager = bannersManager;
    }

    @Override
    public void onItemInteract(Player player) {
        new CosmeticsMenu(bannersManager).openMenu(player);
    }
}
