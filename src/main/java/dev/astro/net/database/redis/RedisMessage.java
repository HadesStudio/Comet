package dev.astro.net.database.redis;

import com.google.gson.Gson;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class RedisMessage {

    private final RedisPayload payload;
    private final Map<String, String> params;

    public RedisMessage(RedisPayload payload) {
        this.payload = payload;
        this.params = new HashMap<>();
    }

    public RedisMessage setParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public String getParam(String key) {
        if (containsParam(key)) {
            return params.get(key);
        }
        return null;
    }

    public boolean containsParam(String key) {
        return params.containsKey(key);
    }

    public void removeParam(String key) {
        if (containsParam(key)) {
            params.remove(key);
        }
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}
