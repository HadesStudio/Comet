package dev.rage.comet.scoreboard.animation;

import dev.rage.comet.CometPlugin;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.utilities.file.FileConfig;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;

@Getter
public class FastBoardTitleAnimation implements Runnable {

    private final int taskId;
    private final List<String> titles;
    private String title;
    private int index;

    private final FileConfig scoreboardFile;

    public FastBoardTitleAnimation(Comet plugin) {
        this.scoreboardFile = plugin.getScoreboardFile();
        this.titles = scoreboardFile.getStringList("title-animation.lines");
        this.title = titles.get(0);
        this.index = 0;
        this.taskId = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(CometPlugin.getPlugin(), this, 20L, (long) (scoreboardFile.getDouble("title-animation.interval") * 20L))
                .getTaskId();
    }

    @Override
    public void run() {
        if (!scoreboardFile.getBoolean("title-animation.enabled")) {
            cancel();
            return;
        }

        if (titles.size() == 0) {
            cancel();
            return;
        }

        if (index == titles.size()) index = 0;

        title = titles.get(index);
        index++;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
