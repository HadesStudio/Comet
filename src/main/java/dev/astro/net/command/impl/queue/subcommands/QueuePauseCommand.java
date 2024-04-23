package dev.astro.net.command.impl.queue.subcommands;

import dev.astro.net.database.redis.RedisManager;
import dev.astro.net.database.redis.RedisMessage;
import dev.astro.net.database.redis.RedisPayload;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.queue.Queue;
import dev.astro.net.model.queue.QueueManager;
import dev.astro.net.utilities.file.FileConfig;
import org.bukkit.command.CommandSender;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueuePauseCommand extends BaseCommand {

    private final FileConfig languageFile;
    private final RedisManager redisManager;
    private final QueueManager queueManager;

    public QueuePauseCommand(Comet plugin) {
        this.languageFile = plugin.getLanguageFile();
        this.redisManager = plugin.getRedisManager();
        this.queueManager = plugin.getQueueManager();
    }

    @Command(name = "queue.pause", permission = "comet.command.queue.pause", inGameOnly = false)
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
                .setParam("queue-pause", String.valueOf(queue.isPaused()))
                .toJSON();
        redisManager.getRedis().write(json);

        if (queue.isPaused()) {
            ChatUtil.sendMessage(sender, languageFile.getString("queue-message.paused")
                    .replace("<queue>", queueName));
        }
        else {
            ChatUtil.sendMessage(sender, languageFile.getString("queue-message.un-paused")
                    .replace("<queue>", queueName));
        }
    }
}
