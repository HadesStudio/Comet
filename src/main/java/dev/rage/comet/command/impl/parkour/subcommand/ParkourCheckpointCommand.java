package dev.rage.comet.command.impl.parkour.subcommand;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.model.parkour.ParkourManager;

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
