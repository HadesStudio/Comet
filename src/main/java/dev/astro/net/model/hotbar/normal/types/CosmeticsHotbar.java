package dev.astro.net.model.hotbar.normal.types;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.menu.CosmeticsMenu;
import dev.astro.net.model.hotbar.normal.NormalHotbar;
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
