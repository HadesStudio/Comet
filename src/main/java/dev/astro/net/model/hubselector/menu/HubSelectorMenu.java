package dev.astro.net.model.hubselector.menu;

import dev.astro.net.CometPlugin;
import dev.astro.net.model.hubselector.HubSelectorItem;
import dev.astro.net.model.hubselector.HubSelectorManager;
import dev.astro.net.utilities.BungeeUtil;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class HubSelectorMenu extends Menu {

    private final HubSelectorManager manager;

    @Override
    public String getTitle(Player player) {
        return manager.getTitle();
    }

    @Override
    public int getSize() {
        return manager.getSize();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (HubSelectorItem hubSelectorItem : manager.getHubSelectorItems()) {
            if (hubSelectorItem == null || (!hubSelectorItem.getPermission().isEmpty()
                    && !player.hasPermission(hubSelectorItem.getPermission())))
                continue;

            buttons.put(hubSelectorItem.getSlot(), new HubSelectorButton(hubSelectorItem));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private static class HubSelectorButton extends Button {

        private final HubSelectorItem hubSelectorItem;

        @Override
        public ItemStack getButtonItem(Player player) {
            return hubSelectorItem.getItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            BungeeUtil.sendBungeeServer(CometPlugin.get(), player, hubSelectorItem.getServerName());
        }
    }
}
