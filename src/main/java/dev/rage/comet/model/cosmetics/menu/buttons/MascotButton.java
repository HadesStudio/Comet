package dev.rage.comet.model.cosmetics.menu.buttons;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.cosmetics.impl.mascots.MascotManager;
import dev.rage.comet.model.cosmetics.impl.mascots.menu.SelectMascotMenu;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class MascotButton extends Button {

    private final Comet plugin;
    private final MascotManager mascotManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.ARMOR_STAND.parseItem())
                .setName(plugin.getCosmeticsFile().getString("menu.mascot_title"))
                .setLore(plugin.getCosmeticsFile().getStringList("menu.mascot_lore"))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new SelectMascotMenu(plugin, mascotManager).openMenu(player);
    }
}
