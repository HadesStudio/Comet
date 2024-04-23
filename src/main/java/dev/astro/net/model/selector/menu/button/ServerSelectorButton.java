package dev.astro.net.model.selector.menu.button;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.selector.ServerSelector;
import dev.astro.net.model.selector.menu.SubServerSelectorMenu;
import dev.astro.net.utilities.BungeeUtil;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
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
