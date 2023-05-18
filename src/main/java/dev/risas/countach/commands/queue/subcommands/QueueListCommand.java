package dev.risas.countach.commands.queue.subcommands;

import dev.risas.countach.Countach;
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

public class QueueListCommand extends BaseCommand {

    private final QueueManager queueManager;

    public QueueListCommand(Countach plugin) {
        this.queueManager = plugin.getQueueManager();
    }

    @Command(name = "queue.list", permission = "countach.command.queue.list", inGameOnly = false)
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
