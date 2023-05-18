package dev.risas.countach.commands.spawn;

import dev.risas.countach.Countach;
import dev.risas.countach.model.spawn.SpawnManager;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends BaseCommand {

	private final SpawnManager spawnManager;

	public SetSpawnCommand(Countach plugin) {
		this.spawnManager = plugin.getSpawnManager();
	}

	@Command(name = "setspawn", permission = "countach.command.setspawn")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();

		spawnManager.setLocation(player.getLocation());
		ChatUtil.sendMessage(player, "&aSpawn location has been set.");
	}
}
