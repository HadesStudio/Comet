package dev.astro.net.model.cosmetics.menu.buttons;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.outfits.OutfitManager;
import dev.astro.net.model.cosmetics.impl.outfits.menu.OutfitMenu;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class OutfitButton extends Button {

    private final Comet plugin;
    private final OutfitManager outfitManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.GOLDEN_CHESTPLATE.parseItem())
                .setName(plugin.getCosmeticsFile().getString("menu.outfit_title"))
                .setLore(plugin.getCosmeticsFile().getStringList("menu.outfit_lore"))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new OutfitMenu(plugin, outfitManager).openMenu(player);
    }
}
