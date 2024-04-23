package dev.astro.net.model.cosmetics.impl.outfits.entries;

import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.ChatUtil;
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
        ItemStack[] armor = new ItemStack[4];

        armor[0] = new ItemStack(Material.LEATHER_BOOTS);
        armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
        armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
        armor[3] = new ItemStack(Material.AIR);

        return armor;
    }

    public void apply(Comet plugin, Player player) {
        player.getInventory().setArmorContents(getArmor());
        player.sendMessage(ChatUtil.translate(plugin.getLanguageFile().getString("cosmetic-message.outfit.applied")
                .replace("<outfit>", getName())));
    }
}
