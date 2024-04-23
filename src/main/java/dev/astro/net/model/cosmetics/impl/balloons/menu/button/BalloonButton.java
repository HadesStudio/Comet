package dev.astro.net.model.cosmetics.impl.balloons.menu.button;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.balloons.BalloonHead;
import dev.astro.net.user.User;
import dev.astro.net.user.UserManager;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class BalloonButton extends Button {

    private final Comet plugin;
    private final BalloonHead balloonHead;
    private final UserManager userManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return balloonHead.getHead().clone();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (!balloonHead.hasPermission(player)) {
            playFail(player);
            ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.no-permission"));
            return;
        }

        User user = userManager.getUser(player.getUniqueId());
        user.applyBalloon(plugin, player, balloonHead);
        ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.balloon.applied")
                .replace("<balloon>", balloonHead.getId()));
        close(player);
    }
}
