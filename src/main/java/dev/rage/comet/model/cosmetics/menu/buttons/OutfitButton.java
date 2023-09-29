package dev.rage.comet.model.cosmetics.menu.buttons;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.cosmetics.impl.outfits.OutfitManager;
import dev.rage.comet.model.cosmetics.impl.outfits.menu.OutfitMenu;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
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
