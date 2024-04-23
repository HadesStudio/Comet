package dev.astro.net.model.hotbar.custom;

import dev.astro.net.utilities.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Risas
 * Project: Comet
 * Date: 14-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter @Setter
public class CustomHotbar {

    private String name;
    private boolean enabled;
    private ItemStack item;
    private int itemSlot;
    private String skullOwner;
    private List<String> commands;

    public ItemStack getItem(Player player) {
        ItemStack newItem = item.clone();

        if (skullOwner.equals("<player>")) {
            newItem = new ItemBuilder(newItem)
                    .setSkullOwner(player.getName())
                    .build();
        }

        return newItem;
    }

    public boolean isSimilar(ItemStack toCheck) {
        return (toCheck != null)
                && (toCheck.getType() != Material.AIR)
                && (toCheck.hasItemMeta())
                && (toCheck.getItemMeta().getDisplayName() != null)
                && toCheck.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName());
    }
}
