package dev.astro.net.model.cosmetics.impl.outfits.menu;

import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.outfits.OutfitManager;
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
public class OutfitMenu extends Menu {

    private final Comet plugin;
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

        buttons.put(getSlot(3, 1), new LeatherOutfitButton());

        buttons.put(getSlot(5, 1), new CustomOutfitButton());

        return buttons;
    }

    private class LeatherOutfitButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(com.cryptomorin.xseries.XMaterial.LEATHER_CHESTPLATE.parseMaterial())
                    .setName(ChatColor.GREEN + "Leather Outfits")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new LeatherOutfitMenu(plugin, outfitManager).openMenu(player);
        }
    }

    private class CustomOutfitButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.parseItem())
                    .setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNjNTgyMThmMjA3NWE1NjhlY2ZmZTVkZmNiNzk2NzZiMzIxMDFkNzE3MmUxNWZjZWRhMWMwNjY4NTdjNDNiMCJ9fX0=")
                    .setName(ChatColor.GREEN + "Custom Outfits")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new CustomOutfitMenu(plugin, outfitManager).openMenu(player);
        }
    }
}
