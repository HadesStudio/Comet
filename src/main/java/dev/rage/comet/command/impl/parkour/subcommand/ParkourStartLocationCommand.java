package dev.rage.comet.command.impl.parkour.subcommand;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.model.parkour.ParkourManager;
import dev.rage.comet.utilities.ChatUtil;
import org.bukkit.entity.Player;

public class ParkourStartLocationCommand extends BaseCommand {

    private final ParkourManager parkourManager;

    public ParkourStartLocationCommand(Comet plugin) {
        this.parkourManager = plugin.getParkourManager();
    }

    @Command(name = "parkour.startlocation", permission = "comet.command.parkour.startlocation", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        parkourManager.setStartLocation(player.getLocation());
        ChatUtil.sendMessage(player, "&aParkour start location set!");
    }
}
