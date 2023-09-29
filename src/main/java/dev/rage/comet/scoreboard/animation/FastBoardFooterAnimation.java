package dev.rage.comet.scoreboard.animation;

import dev.rage.comet.CometPlugin;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.utilities.file.FileConfig;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;

@Getter
public class FastBoardFooterAnimation implements Runnable {

    private final int taskId;
    private final List<String> footers;
    private String footer;
    private int index;

    private final FileConfig scoreboardFile;

    public FastBoardFooterAnimation(Comet plugin) {
        this.scoreboardFile = plugin.getScoreboardFile();
        this.footers = scoreboardFile.getStringList("footer-animation.lines");
        this.footer = footers.get(0);
        this.index = 0;
        this.taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(CometPlugin.getPlugin(), this, 20L, (long) (scoreboardFile.getDouble("footer-animation.interval") * 20L))
                .getTaskId();
    }

    @Override
    public void run() {
        if (!scoreboardFile.getBoolean("footer-animation.enabled")) {
            cancel();
            return;
        }

        if (footers.size() == 0) {
            cancel();
            return;
        }

        if (index == footers.size()) index = 0;

        footer = footers.get(index);
        index++;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
