package dev.rage.comet.model.cosmetics.impl.outfits.menus;

import com.google.common.collect.Maps;
import dev.rage.comet.model.cosmetics.impl.outfits.OutfitManager;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.LeatherOutfit;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class LeatherOutfitMenu extends Menu {

    private final OutfitManager outfitManager;

    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Leather Outfits";
    }

    @Override
    public int getSize() {
        return 9 * 5;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        int f = 1;
        int c = 1;
        for (LeatherOutfit leatherOutfit : outfitManager.getLeatherOutfits()) {
            if (c == 8) {
                c = 1;
                f++;
            }

            buttons.put(getSlot(f, c++), new LeatherButton(leatherOutfit));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private class LeatherButton extends Button {

        private final LeatherOutfit leatherOutfit;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(leatherOutfit.getArmor()[2])
                    .setName(leatherOutfit.getColor() + leatherOutfit.getName() + " Outfit")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            outfitManager.apply(player, leatherOutfit);
        }
    }
}
