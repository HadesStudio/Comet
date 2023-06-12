package dev.rage.comet.model.cosmetics.impl.outfits.entries;

import dev.rage.comet.utilities.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Outfit {

    public abstract String getName();

    public abstract String getDisplayName();

    public ItemStack[] getArmor() {
        return null;
    }

    public String getPermission() {
        return null;
    }

    public ItemStack[] getLeatherArmor() {
        ItemStack[] armor = new ItemStack[3];

        armor[0] = new ItemStack(Material.LEATHER_BOOTS);
        armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
        armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);

        return armor;
    }

    public void apply(Player player) {
        player.getInventory().setArmorContents(getArmor());
        player.sendMessage(ChatUtil.translate("&aYou have equipped the " + getName() + " &aOutfit."));
    }
}
