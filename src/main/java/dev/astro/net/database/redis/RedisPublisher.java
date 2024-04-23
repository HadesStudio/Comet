package dev.astro.net.database.redis;

import com.google.gson.Gson;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.model.queue.Queue;
import redis.clients.jedis.JedisPubSub;

import java.util.Objects;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class RedisPublisher extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        RedisMessage redisMessage = new Gson().fromJson(message, RedisMessage.class);

        if (Objects.requireNonNull(redisMessage.getPayload()) == RedisPayload.QUEUE_PAUSE_UPDATE) {
            String queueName = redisMessage.getParam("queue-name");

            if (!CometPlugin.get().getQueueManager().exists(queueName)) return;

            Queue queue = CometPlugin.get().getQueueManager().getQueue(queueName);
            queue.setPaused(!Boolean.parseBoolean(redisMessage.getParam("queue-pause")));
        } else {
            ChatUtil.logger("&c[Redis] The message was received, but there was no response");
        }
    }
}
