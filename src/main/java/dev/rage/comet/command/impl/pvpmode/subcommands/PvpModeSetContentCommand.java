package dev.rage.comet.command.impl.pvpmode.subcommands;

import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.model.pvpmode.PvpModeManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.file.FileConfig;
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
