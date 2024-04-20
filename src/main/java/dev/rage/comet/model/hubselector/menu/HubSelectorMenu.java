package dev.rage.comet.model.hubselector.menu;

import dev.rage.comet.CometPlugin;
import dev.rage.comet.model.hubselector.HubSelectorItem;
import dev.rage.comet.model.hubselector.HubSelectorManager;
import dev.rage.comet.utilities.BungeeUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
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
