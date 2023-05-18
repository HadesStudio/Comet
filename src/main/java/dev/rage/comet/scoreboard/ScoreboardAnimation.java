package dev.rage.comet.scoreboard;

import dev.rage.comet.Comet;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class ScoreboardAnimation {

    public String title, footer;

    public ScoreboardAnimation(Comet plugin) {
        List<String> titles = plugin.getScoreboardFile().getStringList("title-animation.lines");
        List<String> footers = plugin.getScoreboardFile().getStringList("footer-animation.lines");

        title = titles.get(0);
        footer = footers.get(0);

        if (plugin.getScoreboardFile().getBoolean("title-animation.enabled")) {
            AtomicInteger atomicInteger = new AtomicInteger();

            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
                if (atomicInteger.get() == titles.size()) atomicInteger.set(0);

                title = titles.get(atomicInteger.get());
                atomicInteger.getAndIncrement();
            }, 0L, plugin.getScoreboardFile().getLong("title-animation.ticks") * 20L);
        }

        if (plugin.getScoreboardFile().getBoolean("footer-animation.enabled")) {
            AtomicInteger atomicInteger = new AtomicInteger();

            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
                if (atomicInteger.get() == footers.size()) atomicInteger.set(0);

                footer = footers.get(atomicInteger.get());
                atomicInteger.getAndIncrement();
            }, 0L, plugin.getScoreboardFile().getLong("footer-animation.ticks") * 20L);
        }
    }
}
