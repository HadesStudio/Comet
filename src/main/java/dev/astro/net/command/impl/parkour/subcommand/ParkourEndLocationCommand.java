package dev.astro.net.command.impl.parkour.subcommand;

import dev.astro.net.utilities.Comet;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.model.parkour.ParkourManager;

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
