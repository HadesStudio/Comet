package dev.astro.net.model.selector;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @Setter
public class SubServerSelector {

    private final String name;
    private ItemStack icon;
    private int iconSlot;
    private String subServer, server;
    private List<String> commands;

    public SubServerSelector(String name) {
        this.name = name;
    }

    public void executeCommands(Player player) {
        for (String command : commands) {
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
