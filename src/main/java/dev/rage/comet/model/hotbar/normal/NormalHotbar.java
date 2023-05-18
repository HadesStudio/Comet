package dev.rage.comet.model.hotbar.normal;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.Setter;
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
                && (toCheck.getType() != XMaterial.AIR.parseMaterial())
                && (toCheck.hasItemMeta())
                && (toCheck.getItemMeta().getDisplayName() != null)
                && toCheck.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName());
    }

    public abstract void onItemInteract(Player player);
}
