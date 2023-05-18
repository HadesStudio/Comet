package dev.risas.countach.commands.spawn;

import dev.risas.countach.Countach;
import dev.risas.countach.model.spawn.SpawnManager;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.entity.Player;

public class SpawnCommand extends BaseCommand {

	private final SpawnManager spawnManager;

	public SpawnCommand(Countach plugin) {
		this.spawnManager = plugin.getSpawnManager();
	}

	@Command(name = "spawn")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		spawnManager.toSpawn(player);
		ChatUtil.sendMessage(player, "&aYou have been teleported to spawn.");
	}
}
