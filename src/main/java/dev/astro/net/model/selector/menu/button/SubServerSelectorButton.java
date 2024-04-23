package dev.astro.net.model.selector.menu.button;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.selector.SubServerSelector;
import dev.astro.net.utilities.BungeeUtil;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class SubServerSelectorButton extends Button {

    private final Comet plugin;
    private final SubServerSelector subServerSelector;

    public SubServerSelectorButton(Comet plugin, SubServerSelector subServerSelector) {
        this.plugin = plugin;
        this.subServerSelector = subServerSelector;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemStack itemStack = subServerSelector.getIcon().clone();
        return new ItemBuilder(itemStack)
                .setName(ChatUtil.placeholder(player, itemStack.getItemMeta().getDisplayName()))
                .setLore(ChatUtil.placeholder(player, itemStack.getItemMeta().getLore()))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        String subServer = subServerSelector.getSubServer();

        if (!subServer.isEmpty()) BungeeUtil.sendBungeeServer(plugin, player, subServer);

        subServerSelector.executeCommands(player);
    }
}
