package dev.risas.countach.commands.queue.subcommands;

import dev.risas.countach.Countach;
import dev.risas.countach.model.queue.Queue;
import dev.risas.countach.model.queue.QueueManager;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueJoinCommand extends BaseCommand {

    private final QueueManager queueManager;

    public QueueJoinCommand(Countach plugin) {
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
            ChatUtil.sendMessage(player, "&cQueue with name '" + queueName + "' does not exist.");
            return;
        }

        Queue queuePlayer = queueManager.getQueueByPlayer(player);

        if (queuePlayer != null) {
            ChatUtil.sendMessage(player, "&cYou are already in &c&l" + queueName + " &cqueue.");
            return;
        }

        Queue queue = queueManager.getQueue(queueName);
        queueManager.addQueuePlayer(queue, player.getUniqueId());
        ChatUtil.sendMessage(player, "&aYou have joined &a&l" + queueName + " &aqueue.");
    }
}
