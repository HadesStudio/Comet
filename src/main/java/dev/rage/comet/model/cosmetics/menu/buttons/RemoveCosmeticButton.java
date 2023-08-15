package dev.rage.comet.model.cosmetics.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class RemoveCosmeticButton extends Button {

    private final Comet plugin;
    private final UserManager userManager;

    public RemoveCosmeticButton(Comet plugin) {
        this.plugin = plugin;
        this.userManager = plugin.getUserManager();
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial())
                .setName(plugin.getCosmeticsFile().getString("menu.remove_title"))
                .setLore(plugin.getCosmeticsFile().getStringList("menu.remove_lore"))
                .setData(XMaterial.PLAYER_HEAD.getData())
                .setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1NDgzNjJhMjRjMGZhODQ1M2U0ZDkzZTY4YzU5NjlkZGJkZTU3YmY2NjY2YzAzMTljMWVkMWU4NGQ4OTA2NSJ9fX0=")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        User user = userManager.getUser(player.getUniqueId());
        user.removeBalloon();
        user.removeBanner(player);
        user.removeMascot();
        user.removeOutfit(player);
        user.removeGadget(player);

        ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.remove"));
    }
}
