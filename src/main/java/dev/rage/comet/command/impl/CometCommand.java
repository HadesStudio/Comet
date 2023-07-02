package dev.rage.comet.command.impl;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.Comet;
import org.bukkit.command.CommandSender;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class CometCommand extends BaseCommand {

    private final Comet plugin;

    public CometCommand(Comet plugin) {
        this.plugin = plugin;
    }

    @Command(name = "comet", permission = "comet.command.comet", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length == 0) {
            ChatUtil.sendMessage(sender, "&cUsage: /comet reload");
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.onReload();
            ChatUtil.sendMessage(sender, "&aComet has been reloaded.");
        }
    }
}
