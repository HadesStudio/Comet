package dev.astro.net.model.queue.thread;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.queue.Queue;
import dev.astro.net.model.queue.QueueManager;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueuePositionThread extends Thread {

    private final Comet plugin;
    private final QueueManager queueManager;

    public QueuePositionThread(Comet plugin) {
        this.plugin = plugin;
        this.queueManager = plugin.getQueueManager();
    }

    @Override
    public void run() {
        while (true) {
            for (Queue queue : queueManager.getQueues().values()) {
                if (!queue.isPaused() && !queue.getPlayers().isEmpty()) {
                    Player player = queue.getPlayers().element().getPlayer();

                    if (player != null && player.isOnline()) queueManager.sendToServer(player, queue);
                }
            }

            try {
                Thread.sleep(1000L * plugin.getQueueFile().getLong("queue.position-thread"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
