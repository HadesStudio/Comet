package dev.astro.net.model.timer;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {

    private final Map<String, Timer> timers;

    public TimerManager() {
        this.timers = new HashMap<>();
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }

    public void createTimer(Timer timer) {
        timers.put(timer.getName(), timer);
    }

    public void deleteTimer(Timer timer) {
        timers.remove(timer.getName());
    }

    public boolean existsTimer(String name) {
        return timers.containsKey(name);
    }

    public Timer getTimer(String name) {
        return timers.get(name);
    }
}
