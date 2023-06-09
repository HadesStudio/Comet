package dev.rage.comet.model.cosmetics.impl.outfits.menus;

import com.google.common.collect.Maps;
import dev.rage.comet.model.cosmetics.impl.outfits.OutfitManager;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class OutfitMenu extends Menu {

    private final OutfitManager outfitManager;

    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Outfits";
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(3, 1), new LeatherButton());

        return buttons;
    }

    private class LeatherButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .setName(ChatColor.GREEN + "Leather Outfits")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new LeatherOutfitMenu(outfitManager).openMenu(player);
        }
    }
}
