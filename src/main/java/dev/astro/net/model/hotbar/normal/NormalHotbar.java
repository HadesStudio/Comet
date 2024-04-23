package dev.astro.net.model.hotbar.normal;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Risas
 * Project: Comet
 * Date: 14-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter @Setter
public abstract class NormalHotbar {

    private final String name;
    private boolean enabled;
    private ItemStack item;
    private int itemSlot;
    private String skullOwner;

    protected NormalHotbar(String name) {
        this.name = name;
    }

    public boolean isSimilar(ItemStack toCheck) {
        return (toCheck != null)
                && (toCheck.getType() != Material.AIR)
                && (toCheck.hasItemMeta())
                && (toCheck.getItemMeta().getDisplayName() != null)
                && toCheck.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName());
    }

    public abstract void onItemInteract(Player player);
}
