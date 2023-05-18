package dev.rage.comet.commands.queue.subcommands;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.command.BaseCommand;
import dev.rage.comet.utilities.command.Command;
import dev.rage.comet.utilities.command.CommandArgs;
import dev.rage.comet.Comet;
import dev.rage.comet.model.queue.Queue;
import dev.rage.comet.model.queue.QueueManager;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueLeaveCommand extends BaseCommand {

    private final QueueManager queueManager;

    public QueueLeaveCommand(Comet plugin) {
        this.queueManager = plugin.getQueueManager();
    }

    @Command(name = "queue.leave")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            ChatUtil.sendMessage(player, "&cUsage: /queue leave <queue>");
            return;
        }

        String queueName = args[0];

        if (!queueManager.exists(queueName)) {
            ChatUtil.sendMessage(player, "&cQueue with name '" + queueName + "' does not exist.");
            return;
        }

        Queue queuePlayer = queueManager.getQueueByPlayer(player);

        if (queuePlayer == null) {
            ChatUtil.sendMessage(player, "&cYou are not in any queue.");
            return;
        }

        Queue queue = queueManager.getQueue(queueName);
        queueManager.removeQueuePlayer(queue, player.getUniqueId());

        ChatUtil.sendMessage(player, "&cYou have left &c&l" + queueName + " &cqueue.");
    }
}
