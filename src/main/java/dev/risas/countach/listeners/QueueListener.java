package dev.risas.countach.listeners;

import dev.risas.countach.Countach;
import dev.risas.countach.model.queue.Queue;
import dev.risas.countach.model.queue.QueueManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueListener implements Listener {

    private final QueueManager queueManager;

    public QueueListener(Countach plugin) {
        this.queueManager = plugin.getQueueManager();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Queue queue = queueManager.getQueueByPlayer(player);
        if (queue != null) queueManager.removeQueuePlayer(queue, player.getUniqueId());
    }
}
