package dev.rage.comet.database.redis;

import dev.rage.comet.Comet;
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
