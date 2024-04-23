package dev.astro.net.model.cosmetics.impl.balloons.menu.button;

import dev.astro.net.utilities.Comet;
import dev.astro.net.user.User;
import dev.astro.net.user.UserManager;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class RemoveBalloonButton extends Button {

    private final Comet plugin;
    private final UserManager userManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.parseMaterial())
                .setName("&c&lRemove Balloon")
                .setLore("&7Click to remove your balloon.")
                .setData(com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.getData())
                .setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1NDgzNjJhMjRjMGZhODQ1M2U0ZDkzZTY4YzU5NjlkZGJkZTU3YmY2NjY2YzAzMTljMWVkMWU4NGQ4OTA2NSJ9fX0=")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        User user = userManager.getUser(player.getUniqueId());

        if (!user.hasBalloonModel()) {
            playFail(player);
            ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.balloon.not-applied"));
            return;
        }

        playSuccess(player);
        user.removeBalloon();
        ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.balloon.removed"));
        close(player);
    }
}
