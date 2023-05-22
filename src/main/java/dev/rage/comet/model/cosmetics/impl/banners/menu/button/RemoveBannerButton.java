package dev.rage.comet.model.cosmetics.impl.banners.menu.button;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class RemoveBannerButton extends Button {

    private final UserManager userManager;

    public RemoveBannerButton(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial())
                .setName("&c&lRemove Banner")
                .setLore("&7Click to remove your banner.")
                .setData(XMaterial.PLAYER_HEAD.getData())
                .setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1NDgzNjJhMjRjMGZhODQ1M2U0ZDkzZTY4YzU5NjlkZGJkZTU3YmY2NjY2YzAzMTljMWVkMWU4NGQ4OTA2NSJ9fX0=")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        User user = userManager.getUser(player.getUniqueId());

        if (!user.hasBanner()) {
            playFail(player);
            ChatUtil.sendMessage(player, "&cYou do not have a banner applied.");
            return;
        }

        playSuccess(player);
        user.removeBanner(player);
        ChatUtil.sendMessage(player, "&aYou have removed your banner.");
    }
}
