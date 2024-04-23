package dev.astro.net.model.cosmetics.menu.buttons;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.gadgets.menu.GadgetsMenu;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class GadgetsButton extends Button {

    private final Comet plugin;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.ENDER_CHEST.parseMaterial())
                .setName(plugin.getCosmeticsFile().getString("menu.gadgets_title"))
                .setLore(plugin.getCosmeticsFile().getStringList("menu.gadgets_lore"))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new GadgetsMenu(plugin).openMenu(player);
    }
}
