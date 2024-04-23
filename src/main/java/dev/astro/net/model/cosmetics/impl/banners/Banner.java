package dev.astro.net.model.cosmetics.impl.banners;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Banner {

    public abstract String getId();
    public abstract String getName();
    public abstract ItemStack getItem();

    public boolean hasPermission(Player player) {
        return player.hasPermission("comet.cosmetic.banner." + getId());
    }
}
