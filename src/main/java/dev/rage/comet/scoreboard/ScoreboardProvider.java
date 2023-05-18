package dev.rage.comet.scoreboard;

import dev.rage.comet.Comet;
import dev.rage.comet.model.timer.Timer;
import dev.rage.comet.model.timer.TimerManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.JavaUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProvider implements ScoreboardAdapter {

	private final Comet plugin;
	private final Scoreboard scoreboard;
	private final TimerManager timerManager;

	public ScoreboardProvider(Comet plugin) {
		this.plugin = plugin;
		this.scoreboard = plugin.getScoreboard();
		this.timerManager = plugin.getTimerManager();
	}

	@Override
	public String getTitle(Player player) {
		return scoreboard.getAnimation().getTitle();
	}

	@Override
	public List<String> getLines(Player player) {
		List<String> lines = new ArrayList<>();

		for (String line : plugin.getScoreboardFile().getStringList("scoreboard.lines")) {
			if (line.contains("<queue>")) {
				// TODO: Add queue support
				continue;
			}
			if (line.contains("<timers>")) {
				for (Timer timer : timerManager.getTimers().values()) {
					for (String timerLine : plugin.getScoreboardFile().getStringList("scoreboard.addons.timers")) {
						lines.add(timerLine
								.replace("<timer-name>", timer.getPrefix())
								.replace("<timer-time>", JavaUtil.formatLongHour(timer.getRemaining())));
					}
				}
				continue;
			}
			if (line.contains("<footer>")) {
				lines.add(scoreboard.getAnimation().getFooter());
				continue;
			}

			lines.add(line);
		}

		return ChatUtil.placeholder(player, lines);
	}

	@Override
	public ScoreboardStyle getBoardStyle(Player player) {
		return ScoreboardStyle.KOHI;
	}
}
