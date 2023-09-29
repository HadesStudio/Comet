package dev.rage.comet.command.impl.spawn;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.spawn.SpawnManager;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.entity.Player;

public class SpawnCommand extends BaseCommand {

	private final FileConfig languageFile;
	private final SpawnManager spawnManager;

	public SpawnCommand(Comet plugin) {
		this.languageFile = plugin.getLanguageFile();
		this.spawnManager = plugin.getSpawnManager();
	}

	@Command(name = "spawn")
	@Override
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		spawnManager.toSpawn(player);
		ChatUtil.sendMessage(player, languageFile.getString("spawn-message.teleport"));
	}
}
