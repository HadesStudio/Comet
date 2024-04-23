package dev.astro.net.command.impl.pvpmode.subcommands;

import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.model.pvpmode.PvpModeManager;
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
