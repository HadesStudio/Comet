package dev.astro.net.model.cosmetics.impl.outfits.menu;

import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.outfits.OutfitManager;
import dev.astro.net.model.cosmetics.impl.outfits.entries.impl.CustomOutfit;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class CustomOutfitMenu extends Menu {

    private final Comet plugin;
    private final OutfitManager outfitManager;

    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Custom Outfits";
    }

    @Override
    public int getSize() {
        return 9 * (int) (Math.ceil(outfitManager.getCustomOutfits().size() / 7.0) + 2);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        int f = 1, c = 1;
        for (CustomOutfit customOutfit : outfitManager.getCustomOutfits()) {
            if (c == 8) {
                c = 1;
                f++;
            }

            buttons.put(getSlot(c++, f), new CustomOutfitButton(customOutfit));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private class CustomOutfitButton extends Button {

        private final CustomOutfit customOutfit;

        @Override
        public ItemStack getButtonItem(Player player) {
            ItemStack icon = customOutfit.getArmor()[3];
            return new ItemBuilder(icon == null ? com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.parseItem() : icon)
                    .setName(customOutfit.getDisplayName())
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!customOutfit.hasPermission(player)) {
                playFail(player);
                ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.no-permission"));
                return;
            }

            customOutfit.apply(plugin, player);
        }
    }
}
