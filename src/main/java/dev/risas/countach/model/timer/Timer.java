package dev.risas.countach.model.timer;

import dev.risas.countach.Countach;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Timer {

    private String name;
    private String prefix;
    private long startMillis;
    private long endMillis;
    private boolean running;

    public Timer(Countach plugin, String name, long startMillis, long endMillis, String prefix) {
        this.name = name;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.prefix = prefix;
        this.running = true;

        TimerTask timerTask = new TimerTask(plugin, this);
        timerTask.run();
    }

    public long getRemaining() {
        return endMillis - System.currentTimeMillis();
    }
}
