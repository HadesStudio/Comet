package dev.risas.countach.model.timer;

import dev.risas.countach.Countach;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class TimerTask implements Runnable {

    private final Countach plugin;
    private final Timer timer;
    private final int id;

    public TimerTask(Countach plugin, Timer timer) {
        this.plugin = plugin;
        this.timer = timer;
        this.id = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, 0L, 20L).getTaskId();
    }

    @Override
    public void run() {
        if (timer == null) {
            this.cancel();
            return;
        }
        if (timer.getEndMillis() < System.currentTimeMillis()) {
            this.cancel();
            return;
        }
        if (!timer.isRunning()) {
            this.cancel();
        }
    }

    private void cancel() {
        plugin.getTimerManager().deleteTimer(timer);
        Bukkit.getScheduler().cancelTask(id);
    }
}
