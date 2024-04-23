package dev.astro.net.model.cosmetics.impl.outfits.menu;

import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.outfits.OutfitManager;
import dev.astro.net.model.cosmetics.impl.outfits.entries.impl.LeatherOutfit;
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
public class LeatherOutfitMenu extends Menu {

    private final Comet plugin;
    private final OutfitManager outfitManager;

    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Leather Outfits";
    }

    @Override
    public int getSize() {
        return 9 * (int) (Math.ceil(outfitManager.getLeatherOutfits().size() / 7.0) + 2);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        int f = 1, c = 1;
        for (LeatherOutfit leatherOutfit : outfitManager.getLeatherOutfits()) {
            if (c == 8) {
                c = 1;
                f++;
            }

            buttons.put(getSlot(c++, f), new LeatherOutfitButton(leatherOutfit));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private class LeatherOutfitButton extends Button {

        private final LeatherOutfit leatherOutfit;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(leatherOutfit.getArmor()[2])
                    .setName(leatherOutfit.getDisplayName())
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!leatherOutfit.hasPermission(player)) {
                playFail(player);
                ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.no-permission"));
                return;
            }

            leatherOutfit.apply(plugin, player);
        }
    }
}
