package dev.astro.net.command.impl.parkour.subcommand;

import dev.astro.net.utilities.Comet;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.model.parkour.ParkourManager;
import dev.astro.net.utilities.ChatUtil;
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
