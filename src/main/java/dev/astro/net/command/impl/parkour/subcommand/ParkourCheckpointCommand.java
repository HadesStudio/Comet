package dev.astro.net.command.impl.parkour.subcommand;

import dev.astro.net.utilities.Comet;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.model.parkour.ParkourManager;

public class ParkourCheckpointCommand extends BaseCommand {

    private final ParkourManager parkourManager;

    public ParkourCheckpointCommand(Comet plugin) {
        this.parkourManager = plugin.getParkourManager();
    }

    @Command(name = "parkour.checkpoint", permission = "comet.command.parkour.checkpoint")
    @Override
    public void onCommand(CommandArgs command) {
        command.getPlayer().getInventory().addItem(parkourManager.getCheckpointItem());
    }
}
