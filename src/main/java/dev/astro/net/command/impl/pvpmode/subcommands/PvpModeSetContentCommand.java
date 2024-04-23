package dev.astro.net.command.impl.pvpmode.subcommands;

import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.model.pvpmode.PvpModeManager;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.file.FileConfig;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PvpModeSetContentCommand extends BaseCommand {

    private final FileConfig languageFile;
    private final PvpModeManager pvpModeManager;

    @Command(name = "pvpmode.setcontent", permission = "comet.command.pvpmode.setcontent")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        pvpModeManager.setContent(player.getInventory());
        ChatUtil.sendMessage(player, languageFile.getString("pvpmode-message.set-content"));
    }
}
