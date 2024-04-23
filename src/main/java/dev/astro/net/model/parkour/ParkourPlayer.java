package dev.astro.net.model.parkour;

import dev.astro.net.utilities.JavaUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParkourPlayer {

    private final Player player;
    private final List<Location> checkpoints;
    private long startTime;

    public ParkourPlayer(Player player) {
        this.player = player;
        this.checkpoints = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    public Location getLastCheckpoint() {
        return this.checkpoints.get(checkpoints.size() - 1);
    }

    public void addCheckpoint(Location location) {
        this.checkpoints.add(location);
    }

    public boolean hasCheckpoint(Location location) {
        return this.checkpoints.contains(location);
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public String getElapsedTimeFormatted() {
        return JavaUtil.formatMillis(getElapsedTime());
    }

    public void reset() {
        this.checkpoints.clear();
        this.startTime = System.currentTimeMillis();
    }
}
