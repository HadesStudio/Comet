package dev.rage.comet.database.redis;

import com.google.gson.Gson;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.Comet;
import dev.rage.comet.model.queue.Queue;
import dev.rage.comet.model.queue.QueueManager;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class RedisPublisher extends JedisPubSub {

    private final QueueManager queueManager;

    public RedisPublisher(Comet plugin) {
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
