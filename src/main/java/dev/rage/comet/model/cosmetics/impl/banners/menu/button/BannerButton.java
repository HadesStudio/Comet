package dev.rage.comet.model.cosmetics.impl.banners.menu.button;

import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BannerButton extends Button {

    private final Banner banner;
    private final UserManager userManager;

    public BannerButton(Banner banner, UserManager userManager) {
        this.banner = banner;
        this.userManager = userManager;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return banner.getItem().clone();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        User user = userManager.getUser(player.getUniqueId());

        if (user.hasBanner(banner)) {
            ChatUtil.sendMessage(player, "&cYou already have this banner applied.");
            return;
        }

        user.applyBanner(player, banner);
        ChatUtil.sendMessage(player, "&aYou have applied the " + banner.getName() + " banner.");
    }
}
