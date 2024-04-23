package dev.astro.net.model.queue.rank;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.rank.RankManager;
import dev.astro.net.utilities.file.FileConfig;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueRankManager {

    private final Comet plugin;
    private final Map<String, QueueRank> queueRanks;
    private final Set<String> queueRankNames;
    private final RankManager rankManager;

    public QueueRankManager(Comet plugin) {
        this.plugin = plugin;
        this.queueRanks = new HashMap<>();
        this.queueRankNames = new HashSet<>();
        this.rankManager = plugin.getRankManager();
        this.loadOrRefresh();
    }

    public boolean exists(String name) {
        return queueRanks.containsKey(name);
    }

    public QueueRank getQueueRank(String name) {
        return queueRanks.get(name);
    }

    public QueueRank getQueueRankByRank(UUID uuid) {
        return queueRanks.get(rankManager.getRank().getRankName(uuid));
    }

    public void loadOrCreate(String name, ConfigurationSection section) {
        QueueRank queueRank = exists(name) ? getQueueRank(name) : new QueueRank();
        queueRank.setName(name);
        queueRank.setPriority(section.getInt(name));
        if (!exists(name)) queueRanks.put(name, queueRank);
    }

    public void loadOrRefresh() {
        FileConfig queueFile = plugin.getQueueFile();
        ConfigurationSection section = queueFile.getConfiguration().getConfigurationSection("queue-ranks");

        for (String queueRankName : section.getKeys(false)) {
            loadOrCreate(queueRankName, section);
        }

        for (QueueRank queueRank : queueRanks.values()) {
            String queueRankName = queueRank.getName();
            if (!section.contains(queueRankName)) queueRankNames.add(queueRankName);
        }

        queueRankNames.forEach(queueRanks::remove);
        queueRankNames.clear();
    }

}
