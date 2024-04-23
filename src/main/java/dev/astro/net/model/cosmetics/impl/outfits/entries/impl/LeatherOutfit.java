package dev.astro.net.model.cosmetics.impl.outfits.entries.impl;

import dev.astro.net.model.cosmetics.impl.outfits.entries.Outfit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@Getter
@RequiredArgsConstructor
public class LeatherOutfit extends Outfit {

    private final String name;
    private final ChatColor color;
    private final Color armorColor;

    @Override
    public String getPermission() {
        return "comet.cosmetic.outfit." + getName().toLowerCase().replace(" ", "_");
    }

    @Override
    public String getDisplayName() {
        return color + getName() + " Outfit";
    }

    @Override
    public ItemStack[] getArmor() {
        ItemStack[] armor = getLeatherArmor();

        for (ItemStack item : armor) {
            if (item != null && item.getItemMeta() instanceof LeatherArmorMeta) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(armorColor);
                item.setItemMeta(meta);
            }
        }
        return armor;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }
}
