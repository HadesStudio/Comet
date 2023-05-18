package dev.risas.countach.commands;

import dev.risas.countach.Countach;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by Risas
 * Project: Countach
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class BuildModeCommand extends BaseCommand {

    private final Countach plugin;

    public BuildModeCommand(Countach plugin) {
        this.plugin = plugin;
    }

    @Command(name = "buildmode", aliases = {"build", "bm"}, permission = "countach.command.buildmode")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (player.hasMetadata("countach-build-mode")) {
            player.removeMetadata("countach-build-mode", plugin);
            ChatUtil.sendMessage(player, "&cBuild mode has been disabled.");
        }
        else {
            player.setMetadata("countach-build-mode", new FixedMetadataValue(plugin, true));
            ChatUtil.sendMessage(player, "&aBuild mode has been enabled.");
        }
    }
}
