package dev.rage.comet.scoreboard;

import dev.rage.comet.scoreboard.animation.ScoreboardAnimated;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.queue.Queue;
import dev.rage.comet.model.queue.QueueManager;
import dev.rage.comet.model.timer.Timer;
import dev.rage.comet.model.timer.TimerManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.JavaUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AssembleProvider implements AssembleAdapter {

    private final Comet plugin;
    private final TimerManager timerManager;
    private final QueueManager queueManager;

    public AssembleProvider(Comet plugin) {
        this.plugin = plugin;
        this.timerManager = plugin.getTimerManager();
        this.queueManager = plugin.getQueueManager();
    }

    @Override
    public String getTitle(Player player) {
        return ScoreboardAnimated.getTitle();
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        for (String line : plugin.getScoreboardFile().getStringList("scoreboard.lines")) {
            if (line.contains("<queue>")) {
                Queue queue = queueManager.getQueueByPlayer(player);
                if (queue == null) continue;

                for (String queueLine : plugin.getScoreboardFile().getStringList("scoreboard.addons.queue")) {
                    lines.add(queueLine
                            .replace("<queue-player-name>", queue.getServer())
                            .replace("<queue_player_position>", String.valueOf(queueManager.getQueuePlayerPosition(queue, player.getUniqueId())))
                            .replace("<queue-player-size>", String.valueOf(queue.getPlayers().size())));
                }
                continue;
            }
            if (line.contains("<timers>")) {
                for (Timer timer : timerManager.getTimers().values()) {
                    for (String timerLine : plugin.getScoreboardFile().getStringList("scoreboard.addons.timers")) {
                        lines.add(timerLine
                                .replace("<timer-name>", timer.getPrefix())
                                .replace("<timer-time>", JavaUtil.formatMillis(timer.getRemaining())));
                    }
                }
                continue;
            }
            if (line.contains("<footer>")) {
                lines.add(ScoreboardAnimated.getFooter());
                continue;
            }

            lines.add(line);
        }

        return ChatUtil.placeholder(player, lines);
    }
}
