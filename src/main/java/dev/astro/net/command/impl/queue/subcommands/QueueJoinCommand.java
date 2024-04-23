package dev.astro.net.command.impl.queue.subcommands;

import dev.astro.net.model.queue.player.QueuePlayer;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.queue.Queue;
import dev.astro.net.model.queue.QueueManager;
import dev.astro.net.utilities.file.FileConfig;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueJoinCommand extends BaseCommand {

    private final FileConfig languageFile;
    private final QueueManager queueManager;

    public QueueJoinCommand(Comet plugin) {
        this.languageFile = plugin.getLanguageFile();
        this.queueManager = plugin.getQueueManager();
    }

    @Command(name = "queue.join")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            ChatUtil.sendMessage(player, "&cUsage: /queue join <queue>");
            return;
        }

        String queueName = args[0];

        if (!queueManager.exists(queueName)) {
            ChatUtil.sendMessage(player, languageFile.getString("queue-message.not-exist")
                    .replace("<queue>", queueName));
            return;
        }

        QueuePlayer queuePlayer = queueManager.getQueuePlayer(player.getUniqueId());

        if (queuePlayer != null) {
            ChatUtil.sendMessage(player, languageFile.getString("queue-message.already-in-queue")
                    .replace("<queue>", queueName));
            return;
        }

        Queue queue = queueManager.getQueue(queueName);
        queueManager.addQueuePlayer(queue, player.getUniqueId());
        ChatUtil.sendMessage(player, languageFile.getString("queue-message.joined")
                .replace("<queue>", queueName));
    }
}
