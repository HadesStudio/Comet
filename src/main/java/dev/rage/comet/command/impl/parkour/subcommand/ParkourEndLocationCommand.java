package dev.rage.comet.command.impl.parkour.subcommand;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.model.parkour.ParkourManager;

public class ParkourEndLocationCommand extends BaseCommand {

    private final ParkourManager parkourManager;

    public ParkourEndLocationCommand(Comet plugin) {
        this.parkourManager = plugin.getParkourManager();
    }

    @Command(name = "parkour.endlocation", permission = "comet.command.parkour.endlocation", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        command.getPlayer().getInventory().addItem(parkourManager.getEndLocationItem());
    }
}
