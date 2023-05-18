package dev.rage.comet.commands.spawn;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.command.BaseCommand;
import dev.rage.comet.utilities.command.Command;
import dev.rage.comet.utilities.command.CommandArgs;
import dev.rage.comet.Comet;
import dev.rage.comet.model.spawn.SpawnManager;
import org.bukkit.entity.Player;

public class SpawnCommand extends BaseCommand {

	private final SpawnManager spawnManager;

	public SpawnCommand(Comet plugin) {
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
