package dev.astro.net.model.cosmetics.menu.buttons;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.banners.BannerManager;
import dev.astro.net.model.cosmetics.impl.banners.menu.BannerMenu;
import dev.astro.net.user.UserManager;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
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
