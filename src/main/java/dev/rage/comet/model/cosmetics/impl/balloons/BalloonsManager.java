package dev.rage.comet.model.cosmetics.impl.balloons;

import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.balloons.tasks.BalloonRunnable;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class BalloonsManager {

    private final Map<UUID, Balloon> balloons;

    public BalloonsManager(Comet plugin) {
        this.balloons = Maps.newHashMap();

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BalloonRunnable(this), 0L, 1L);
    }
}
