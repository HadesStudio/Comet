package dev.risas.countach.commands;

import dev.risas.countach.Countach;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * Created by Risas
 * Project: Countach
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class CountachCommand extends BaseCommand {

    private final Countach plugin;

    public CountachCommand(Countach plugin) {
        this.plugin = plugin;
    }

    @Command(name = "countach", permission = "countach.command.countach", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length == 0) {
            ChatUtil.sendMessage(sender, "&cUsage: /countach reload");
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.onReload();
            ChatUtil.sendMessage(sender, "&aCountach has been reloaded.");
        }
    }
}
