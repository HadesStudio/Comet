package dev.astro.net.database.redis;

import dev.astro.net.utilities.Comet;
import lombok.Getter;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class RedisManager {

    private final Redis redis;

    public RedisManager(Comet plugin) {
        this.redis = new Redis(plugin);
    }

    public void onDisable() {
        if (redis.isConnected()) redis.disconnect();
    }
}
