package dev.risas.countach.database.redis;

import com.google.gson.Gson;
import dev.risas.countach.Countach;
import dev.risas.countach.model.queue.Queue;
import dev.risas.countach.model.queue.QueueManager;
import dev.risas.countach.utilities.ChatUtil;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class RedisPublisher extends JedisPubSub {

    private final QueueManager queueManager;

    public RedisPublisher(Countach plugin) {
        this.queueManager = plugin.getQueueManager();
    }

    @Override
    public void onMessage(String channel, String message) {
        RedisMessage redisMessage = new Gson().fromJson(message, RedisMessage.class);

        switch (redisMessage.getPayload()) {
            case QUEUE_PAUSE_UPDATE: {
                String queueName = redisMessage.getParam("queue-name");

                if (!queueManager.exists(queueName)) return;

                Queue queue = queueManager.getQueue(queueName);
                queue.setPaused(!Boolean.parseBoolean(redisMessage.getParam("queue-pause")));
                break;
            }
            default:
                ChatUtil.logger("&c[Redis] The message was received, but there was no response");
                break;
        }
    }
}
