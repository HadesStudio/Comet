package dev.rage.comet.model.cosmetics.impl.outfits.entries;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.awt.*;

@AllArgsConstructor
public abstract class CustomOutfit extends Outfit {

    private final String name;
    private ItemStack customHead;
    private String chestPlateColor, leggingsColor, bootsColor;

    @Override
    public String getPermission() {
        return "comet.outfit.custom." + getName().toLowerCase().replace(" ", "_");
    }

    @Override
    public ItemStack[] getArmor() {
        ItemStack[] armor = getLeatherArmor();

        for (ItemStack item : armor) {
            if (item != null) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor();
                item.setItemMeta(meta);
            }
        }
        return super.getArmor();
    }
}
