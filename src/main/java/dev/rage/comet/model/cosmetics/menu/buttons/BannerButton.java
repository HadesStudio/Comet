package dev.rage.comet.model.cosmetics.menu.buttons;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.banners.BannerManager;
import dev.rage.comet.model.cosmetics.impl.banners.menu.BannerMenu;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class BannerButton extends Button {

    private final Comet plugin;
    private final BannerManager bannerManager;
    private final UserManager userManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.BLACK_BANNER.parseMaterial())
                .setName(plugin.getCosmeticsFile().getString("menu.banner_title"))
                .setLore(plugin.getCosmeticsFile().getStringList("menu.banner_lore"))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        BannerMenu bannerMenu = new BannerMenu(plugin, bannerManager, userManager);
        bannerMenu.openMenu(player);
    }
}
