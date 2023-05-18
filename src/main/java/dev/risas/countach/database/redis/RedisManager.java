package dev.risas.countach.database.redis;

import dev.risas.countach.Countach;
import lombok.Getter;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class RedisManager {

    private final Redis redis;

    public RedisManager(Countach plugin) {
        this.redis = new Redis(plugin);
    }

    public void onDisable() {
        redis.disconnect();
    }
}
