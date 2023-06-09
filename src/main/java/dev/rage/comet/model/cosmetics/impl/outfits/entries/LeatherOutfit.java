package dev.rage.comet.model.cosmetics.impl.outfits.entries;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public abstract class LeatherOutfit {

    public abstract String getName();

    public abstract ChatColor getColor();

    public abstract Color getArmorColor();

    public ItemStack[] getArmor() {
        ItemStack[] armors = new ItemStack[3];

        armors[0] = new ItemStack(Material.LEATHER_BOOTS);
        armors[1] = new ItemStack(Material.LEATHER_LEGGINGS);
        armors[2] = new ItemStack(Material.LEATHER_CHESTPLATE);

        return armors;
    }

    public String getPermission() {
        return "comet.outfit." + getName().toLowerCase().replace(" ", "_");
    }

    private ItemStack[] getColoredArmor() {
        ItemStack[] armor = getArmor();
        for (ItemStack item : armor) {
            if (item != null) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(getArmorColor());
                item.setItemMeta(meta);
            }
        }
        return armor;
    }
}
