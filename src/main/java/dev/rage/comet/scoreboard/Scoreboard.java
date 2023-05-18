package dev.rage.comet.scoreboard;

import dev.rage.comet.scoreboard.events.ScoreboardCreateEvent;
import dev.rage.comet.Comet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class Scoreboard {

	private Comet plugin;
	private ScoreboardAdapter adapter;
	private Map<UUID, ScoreboardManager> boards;
	private ScoreboardThread thread;
	private ScoreboardListener listeners;
	private long ticks = 2;
	private boolean hook = false;
	private ScoreboardStyle assembleStyle = ScoreboardStyle.MODERN;
	private ScoreboardAnimation animation;

	public Scoreboard(Comet plugin) {
		if (plugin == null) {
			throw new RuntimeException("Scoreboard can not be instantiated without a plugin instance!");
		}

		this.plugin = plugin;
		this.boards = new ConcurrentHashMap<>();

		this.setup();
	}

	public void setup() {
		listeners = new ScoreboardListener(this);

		//Register Events
		this.plugin.getServer().getPluginManager().registerEvents(listeners, this.plugin);

		//Ensure that the thread has stopped running
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		//Register new boards for existing online players
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			//Make sure it doesn't double up
			ScoreboardCreateEvent createEvent = new ScoreboardCreateEvent(player);

			Bukkit.getPluginManager().callEvent(createEvent);
			if (createEvent.isCancelled()) {
				return;
			}

			getBoards().put(player.getUniqueId(), new ScoreboardManager(player, this));
		}

		//Start Thread
		this.thread = new ScoreboardThread(this);
		this.animation = new ScoreboardAnimation(plugin);
	}

	public void cleanup() {
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		if (listeners != null) {
			HandlerList.unregisterAll(listeners);
			listeners = null;
		}

		for (UUID uuid : getBoards().keySet()) {
			Player player = Bukkit.getPlayer(uuid);
			getBoards().remove(uuid);
			player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		}
	}
}

