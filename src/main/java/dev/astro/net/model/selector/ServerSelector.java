package dev.astro.net.model.selector;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.file.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class ServerSelector {

    private final String name;
    private ItemStack icon;
    private int iconSlot;
    private String server;
    private List<String> commands;
    private List<SubServerSelector> subServerSelectors;
    private FileConfig subServerFile;

    private String menuTitle;
    private int menuRows;

    public ServerSelector(String name) {
        this.name = name;
        this.subServerSelectors = new ArrayList<>();
        this.subServerFile = new FileConfig(CometPlugin.getPlugin(), "selector/sub-server/" + name + "-server.yml");
    }

    public void loadOrCreateFolder() {
        FileConfiguration configuration = subServerFile.getConfiguration();

        if (configuration.getKeys(true).size() == 0) {
            configuration.set("menu.title", "<server> Server Selector");
            configuration.set("menu.rows", 3);
            configuration.set("sub-servers", Collections.EMPTY_MAP);
            subServerFile.save();
        }

        subServerFile.reload();
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
