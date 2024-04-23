package dev.astro.net.command.impl.pvpmode.subcommands;

import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.model.pvpmode.PvpModeManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PvpModeLeaveCommand extends BaseCommand {

    private final PvpModeManager pvpModeManager;

    @Command(name = "pvpmode.leave")
    @Override
    public void onCommand(CommandArgs command) {
        pvpModeManager.removePlayer(command.getPlayer(), true);
    }
}
