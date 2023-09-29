package dev.rage.comet.model.cosmetics.impl.banners.menu.button;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class BannerButton extends Button {

    private final Comet plugin;
    private final Banner banner;
    private final UserManager userManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return banner.getItem().clone();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (!banner.hasPermission(player)) {
            playFail(player);
            ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.no-permission"));
            return;
        }

        User user = userManager.getUser(player.getUniqueId());

        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() != Material.AIR) {
            ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.banner.cant-use")
                    .replace("<banner>", banner.getId()));
            return;
        }

        playSuccess(player);
        user.applyBanner(player, banner);
        ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.banner.applied")
                .replace("<banner>", banner.getId()));
        close(player);
    }
}
