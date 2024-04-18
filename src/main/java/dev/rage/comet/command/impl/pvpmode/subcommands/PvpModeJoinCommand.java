package dev.rage.comet.command.impl.pvpmode.subcommands;

import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.model.pvpmode.PvpModeManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PvpModeJoinCommand extends BaseCommand {

    private final PvpModeManager pvpModeManager;

    @Command(name = "pvpmode.join")
    @Override
    public void onCommand(CommandArgs command) {
        pvpModeManager.addPlayer(command.getPlayer());
    }
}
