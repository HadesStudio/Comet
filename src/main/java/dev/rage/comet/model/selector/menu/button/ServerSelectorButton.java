package dev.rage.comet.model.selector.menu.button;

import dev.rage.comet.Comet;
import dev.rage.comet.model.selector.ServerSelector;
import dev.rage.comet.model.selector.menu.SubServerSelectorMenu;
import dev.rage.comet.utilities.BungeeUtil;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ServerSelectorButton extends Button {

    private final Comet plugin;
    private final ServerSelector serverSelector;

    public ServerSelectorButton(Comet plugin, ServerSelector serverSelector) {
        this.plugin = plugin;
        this.serverSelector = serverSelector;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemStack itemStack = serverSelector.getIcon().clone();
        return new ItemBuilder(itemStack)
                .setName(ChatUtil.placeholder(player, itemStack.getItemMeta().getDisplayName()))
                .setLore(ChatUtil.placeholder(player, itemStack.getItemMeta().getLore()))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (!serverSelector.getSubServerSelectors().isEmpty()) {
            SubServerSelectorMenu subServerSelectorMenu = new SubServerSelectorMenu(plugin, serverSelector);
            subServerSelectorMenu.openMenu(player);
            return;
        }

        String server = serverSelector.getServer();

        if (!server.isEmpty()) BungeeUtil.sendBungeeServer(plugin, player, server);

        serverSelector.executeCommands(player);
    }
}
