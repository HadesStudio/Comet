package dev.risas.countach.model.queue.thread;

import dev.risas.countach.Countach;
import dev.risas.countach.model.queue.Queue;
import dev.risas.countach.model.queue.QueueManager;
import dev.risas.countach.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueReminderThread extends Thread {

    private final Countach plugin;
    private final QueueManager queueManager;

    public QueueReminderThread(Countach plugin) {
        this.plugin = plugin;
        this.queueManager = plugin.getQueueManager();
    }

    @Override
    public void run() {
        while (true) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Queue queue = queueManager.getQueueByPlayer(player);

                if (queue != null) {
                    ChatUtil.sendMessage(player, "&7You are currently in queue for &f" + queue.getServer() + "&7.");
                    ChatUtil.sendMessage(player, "&7You are currently &f" + queueManager.getQueuePlayerPosition(queue, player.getUniqueId()) + "/" + queue.getPlayers().size() + " &7in queue.");
                }
            }

            try {
                Thread.sleep(1000L * plugin.getQueueFile().getLong("queue.position-message-thread"));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
