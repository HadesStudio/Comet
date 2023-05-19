package dev.rage.comet.model.cosmetics.menu.buttons;

import dev.rage.comet.model.cosmetics.impl.banners.BannersManager;
import dev.rage.comet.model.cosmetics.impl.banners.menu.BannersMenu;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class BannersButton extends Button {

    private final BannersManager bannersManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.BANNER)
                .setName("&6&lBanners")
                .setLore("",
                        "&7Click to view all banners.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new BannersMenu(bannersManager).openMenu(player);
    }
}
