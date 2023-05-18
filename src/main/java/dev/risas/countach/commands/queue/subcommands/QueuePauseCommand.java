package dev.risas.countach.commands.queue.subcommands;

import dev.risas.countach.Countach;
import dev.risas.countach.database.redis.RedisManager;
import dev.risas.countach.database.redis.RedisMessage;
import dev.risas.countach.database.redis.RedisPayload;
import dev.risas.countach.model.queue.Queue;
import dev.risas.countach.model.queue.QueueManager;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueuePauseCommand extends BaseCommand {

    private final RedisManager redisManager;
    private final QueueManager queueManager;

    public QueuePauseCommand(Countach plugin) {
        this.redisManager = plugin.getRedisManager();
        this.queueManager = plugin.getQueueManager();
    }

    @Command(name = "queue.pause", permission = "countach.command.queue.pause", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length == 0) {
            ChatUtil.sendMessage(sender, "&cUsage: /queue pause <queue>");
            return;
        }

        String queueName = args[0];

        if (!queueManager.exists(queueName)) {
            ChatUtil.sendMessage(sender, "&cQueue with name '" + queueName + "' does not exist.");
            return;
        }

        Queue queue = queueManager.getQueue(queueName);
        String json = new RedisMessage(RedisPayload.QUEUE_PAUSE_UPDATE)
                .setParam("queue-name", queueName)
                .setParam("queue-pause", String.valueOf(queue.isPaused())).toJSON();
        redisManager.getRedis().write(json);
        ChatUtil.sendMessage(sender, "&eQueue &6" + queueName + " &ehas been " + (!queue.isPaused() ? "&cpaused" : "&aunpaused") + "&e.");
    }
}
