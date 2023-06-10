package dev.rage.comet.model.cosmetics.impl.outfits.entries;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public abstract class LeatherOutfit extends Outfit{

    public abstract ChatColor getColor();

    public abstract Color getArmorColor();

    @Override
    public String getPermission() {
        return "comet.outfit.leather." + getName().toLowerCase().replace(" ", "_");
    }

    @Override
    public ItemStack[] getArmor() {
        ItemStack[] armor = getLeatherArmor();

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
