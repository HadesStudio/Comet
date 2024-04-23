package dev.astro.net.command.impl.spawn;

import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.spawn.SpawnManager;
import dev.astro.net.utilities.file.FileConfig;
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
