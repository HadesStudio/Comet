package dev.astro.net.listeners;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.queue.Queue;
import dev.astro.net.model.queue.QueueManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueListener implements Listener {

    private final QueueManager queueManager;

    public QueueListener(Comet plugin) {
        this.queueManager = plugin.getQueueManager();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Queue queue = queueManager.getQueueByPlayer(player);
        if (queue != null) queueManager.removeQueuePlayer(queue, player.getUniqueId());
    }
}
