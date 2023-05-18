package dev.rage.comet.model.queue.thread;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.Comet;
import dev.rage.comet.model.queue.Queue;
import dev.rage.comet.model.queue.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueReminderThread extends Thread {

    private final Comet plugin;
    private final QueueManager queueManager;

    public QueueReminderThread(Comet plugin) {
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
