package dev.rage.comet.model.selector.server.menu.button;

import dev.rage.comet.Comet;
import dev.rage.comet.model.selector.server.ServerSelector;
import dev.rage.comet.utilities.BungeeUtil;
import dev.rage.comet.utilities.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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
        return serverSelector.getIcon();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        String server = serverSelector.getServer();

        if (!server.isEmpty()) BungeeUtil.sendBungeeServer(plugin, player, server);

        for (String command : serverSelector.getCommands()) {
            CommandSender sender = Bukkit.getConsoleSender();
            boolean isPlayer = command.contains("player:");

            if (isPlayer) {
                sender = player;
                command = command.replace("player:", "");
            }

            Bukkit.dispatchCommand(sender, command
                    .replace("<player>", player.getName()));
        }
    }
}
