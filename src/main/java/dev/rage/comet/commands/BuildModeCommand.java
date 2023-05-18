package dev.rage.comet.commands;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.command.BaseCommand;
import dev.rage.comet.utilities.command.Command;
import dev.rage.comet.utilities.command.CommandArgs;
import dev.rage.comet.Comet;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class BuildModeCommand extends BaseCommand {

    private final Comet plugin;

    public BuildModeCommand(Comet plugin) {
        this.plugin = plugin;
    }

    @Command(name = "buildmode", aliases = {"build", "bm"}, permission = "comet.command.buildmode")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (player.hasMetadata("comet-build-mode")) {
            player.removeMetadata("comet-build-mode", plugin);
            ChatUtil.sendMessage(player, "&cBuild mode has been disabled.");
        }
        else {
            player.setMetadata("comet-build-mode", new FixedMetadataValue(plugin, true));
            ChatUtil.sendMessage(player, "&aBuild mode has been enabled.");
        }
    }
}
