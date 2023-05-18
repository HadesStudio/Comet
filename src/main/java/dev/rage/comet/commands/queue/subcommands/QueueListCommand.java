package dev.rage.comet.commands.queue.subcommands;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.command.BaseCommand;
import dev.rage.comet.utilities.command.Command;
import dev.rage.comet.utilities.command.CommandArgs;
import dev.rage.comet.Comet;
import dev.rage.comet.model.queue.Queue;
import dev.rage.comet.model.queue.QueueManager;
import org.bukkit.command.CommandSender;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueListCommand extends BaseCommand {

    private final QueueManager queueManager;

    public QueueListCommand(Comet plugin) {
        this.queueManager = plugin.getQueueManager();
    }

    @Command(name = "queue.list", permission = "comet.command.queue.list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        ChatUtil.sendMessage(sender, "&7&m--------->&r &6&lQueue List &7&m<---------");

        if (queueManager.getQueues().isEmpty()) {
            ChatUtil.sendMessage(sender, "&cThere are no queues.");
        }
        else {
            for (Queue queue : queueManager.getQueues().values()) {
                ChatUtil.sendMessage(sender, " &7▶ &e" + queue.getServer() + " Queue");
            }
        }

        ChatUtil.sendMessage(sender, ChatUtil.NORMAL_LINE);
    }
}
