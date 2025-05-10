package dev.astro.net.utilities;

import dev.astro.net.CometPlugin;
import dev.astro.net.model.actionbar.ActionBarRunnable;
import dev.astro.net.model.cosmetics.impl.balloons.tasks.BalloonRunnable;
import dev.astro.net.model.lunarclient.LunarClientRunnable;
import dev.astro.net.model.queue.thread.QueuePositionThread;
import dev.astro.net.model.queue.thread.QueueReminderThread;
import es.hulk.licensesystem.LicenseImplementation;
import lombok.Getter;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

@Getter
public class Plugin {

	private final String licenseKey;
	private final org.bukkit.plugin.Plugin pluginClass;

	private final String product;
	private final String version;

	public Plugin(String licenseKey, org.bukkit.plugin.Plugin pluginClass) {
		this.licenseKey = licenseKey;
		this.pluginClass = pluginClass;
		this.product = pluginClass.getName();
		this.version = pluginClass.getDescription().getVersion();
		LicenseImplementation licenseImplementation = new LicenseImplementation(true, licenseKey, "Comet", version);
		if (licenseImplementation.isLicenseValid()) {

			Arrays.asList(
				"      &f&l* &b&lComet &7(&fHubCore&7) &f&l*       ",
				"",
				" &f&l* &bAuthor&7: &f" + CometPlugin.getPlugin().getDescription().getAuthors(),
				" &f&l* &bPlugin Version&7: &f" + CometPlugin.getPlugin().getDescription().getVersion(),

				" &f&l* &bLicense Status&7: &aVALID"
			).forEach(s -> Bukkit.getConsoleSender().sendMessage(ChatUtil.translate(s)));

			CometPlugin.get().registerManagers();
			CometPlugin.get().registerListeners();
			CometPlugin.get().registerCommands();

			if (CometPlugin.get().getConfigFile().getBoolean("lunar-client.enabled")) {
				Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new LunarClientRunnable(CometPlugin.get()), 0L, 20L);
			}
			if (CometPlugin.get().getConfigFile().getBoolean("action-bar.enabled")) {
				int interval = CometPlugin.get().getConfigFile().getInt("action-bar.interval");
				Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new ActionBarRunnable(CometPlugin.get(), interval), interval * 20L, interval * 20L);
			}

			QueueReminderThread reminderThread = new QueueReminderThread(CometPlugin.get());
			reminderThread.start();

			QueuePositionThread positionThread = new QueuePositionThread(CometPlugin.get());
			positionThread.start();

			CometPlugin.get().removeBuggedEntities();

			Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new BalloonRunnable(CometPlugin.get().getUserManager()), 0L, 1L);
			Bukkit.getMessenger().registerOutgoingPluginChannel(CometPlugin.getPlugin(), "BungeeCord");
		}
	}
}
