package dev.astro.net.database.redis;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class Redis {

    private JedisPool jedisPool;
    private RedisPublisher redisPublisher;
    private boolean connected;

    public Redis(Comet plugin) {
        try {
            this.jedisPool = new JedisPool("localhost", 6379);

            Jedis jedis = jedisPool.getResource();

            String password = plugin.getDatabaseFile().getString("redis.password");
            if (!password.isEmpty()) jedis.auth(password);

            this.redisPublisher = new RedisPublisher();

            (new Thread(() -> jedis.subscribe(redisPublisher, "Comet"))).start();
            jedis.connect();

            connected = true;
            ChatUtil.logger("&a[" + CometPlugin.getPlugin().getName() + "] Redis successfully connected.");
        }
        catch (Exception exception) {
            ChatUtil.logger("&c[" + CometPlugin.getPlugin().getName() + "] &cRedis failed to connect.");
        }
    }

    public void disconnect() {
        jedisPool.destroy();
        redisPublisher.unsubscribe();
    }

    public void write(String json) {
        Jedis jedis = jedisPool.getResource();
        jedis.publish("Comet", json);
    }
}
