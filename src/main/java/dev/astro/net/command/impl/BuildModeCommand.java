package dev.astro.net.command.impl;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.file.FileConfig;
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
    private final FileConfig languageFile;

    public BuildModeCommand(Comet plugin) {
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
    }

    @Command(name = "buildmode", aliases = {"build", "bm"}, permission = "comet.command.buildmode")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (player.hasMetadata("comet-build-mode")) {
            player.removeMetadata("comet-build-mode", CometPlugin.getPlugin());
            ChatUtil.sendMessage(player, languageFile.getString("build-mode-message.disabled"));
        }
        else {
            player.setMetadata("comet-build-mode", new FixedMetadataValue(CometPlugin.getPlugin(), true));
            ChatUtil.sendMessage(player, languageFile.getString("build-mode-message.enabled"));
        }
    }
}
