package dev.astro.net.model.queue;

import dev.astro.net.model.queue.player.QueuePlayer;
import dev.astro.net.model.queue.rank.QueueRankManager;
import dev.astro.net.utilities.BungeeUtil;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.Comet;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Risas
 * Project: Comet
 * Date: 19-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class QueueManager {

    private final Comet plugin;
    private final Map<String, Queue> queues;
    private final Set<String> queueNames;

    private final QueueRankManager queueRankManager;

    public QueueManager(Comet plugin) {
        this.plugin = plugin;
        this.queues = new LinkedHashMap<>();
        this.queueNames = new HashSet<>();
        this.queueRankManager = plugin.getQueueRankManager();
        this.loadOrRefresh();
    }

    public boolean exists(String server) {
        return queues.containsKey(server);
    }

    public boolean isQueuePlayer(UUID uuid) {
        for (Queue queue : queues.values()) {
            for (QueuePlayer queuePlayer : queue.getPlayers()) {
                if (queuePlayer.getUuid().equals(uuid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Queue getQueue(String server) {
        return queues.get(server);
    }

    public Queue getQueueByPlayer(Player player) {
        for (Queue queue : queues.values()) {
            boolean noneMatch = queue.getPlayers()
                    .stream()
                    .noneMatch(queuePlayer -> queuePlayer.getUuid().equals(player.getUniqueId()));

            if (noneMatch) {
                return null;
            }

            return queue;
        }
        return null;
    }

    public QueuePlayer getQueuePlayer(UUID uuid) {
        for (Queue queue : queues.values()) {
            return queue.getPlayers()
                    .stream()
                    .filter(queuePlayer -> queuePlayer.getUuid().equals(uuid))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public int getQueuePlayerPosition(Queue queue, UUID uuid) {
        if (!isQueuePlayer(uuid)) return 0;

        PriorityQueue<QueuePlayer> queuePriority = new PriorityQueue<>(queue.getPlayers());

        int position = 0;

        while (!queuePriority.isEmpty()) {
            QueuePlayer player = queuePriority.poll();
            if (player.getUuid().equals(uuid)) break;
            position++;
        }

        return position + 1;
    }

    public void addQueuePlayer(Queue queue, UUID uuid) {
        QueuePlayer queuePlayer = new QueuePlayer(uuid);
        queuePlayer.setInserted(System.currentTimeMillis());
        queuePlayer.setRank(queueRankManager.getQueueRankByRank(uuid));
        queue.getPlayers().add(queuePlayer);
    }

    public void removeQueuePlayer(Queue queue, UUID uuid) {
        QueuePlayer queuePlayer = getQueuePlayer(uuid);
        queue.getPlayers().remove(queuePlayer);
    }

    public void sendToServer(Player player, Queue queue) {
        BungeeUtil.sendBungeeServer(plugin, player, queue.getServer());
        this.removeQueuePlayer(queue, player.getUniqueId());
    }

    public void loadOrCreate(String name) {
        Queue queue = exists(name) ? getQueue(name) : new Queue();
        queue.setServer(name);
        if (!exists(name)) queues.put(name, queue);
    }

    public void loadOrRefresh() {
        FileConfig queueFile = plugin.getQueueFile();

        for (String queueName : queueFile.getStringList("queue.servers")) {
            loadOrCreate(queueName);
        }

        for (Queue queue : queues.values()) {
            String queueName = queue.getServer();
            if (!queueFile.getStringList("queue.servers").contains(queueName)) queueNames.add(queueName);
        }

        queueNames.forEach(queues::remove);
        queueNames.clear();
    }
}
