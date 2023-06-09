package dev.rage.comet.model.cosmetics.impl.outfits.entries;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Outfit {

    public abstract String getName();

    public ItemStack[] getArmor() {
        return null;
    }

    public String getPermission() {
        return null;
    }

    public ItemStack[] getLeatherArmor() {
        /*if (head) {
            ItemStack[] armor = new ItemStack[4];

            armor[0] = new ItemStack(Material.LEATHER_BOOTS);
            armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
            armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
            armor[3] = new ItemStack(Material.LEATHER_HELMET);

            return armor;
        } else {*/
            ItemStack[] armor = new ItemStack[3];

            armor[0] = new ItemStack(Material.LEATHER_BOOTS);
            armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
            armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);

            return armor;
        /*}*/
    }
}
