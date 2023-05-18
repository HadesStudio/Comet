package dev.risas.countach.model.queue.rank;

import dev.risas.countach.Countach;
import dev.risas.countach.utilities.file.FileConfig;
import me.activated.core.api.player.PlayerData;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueRankManager {

    private final Countach plugin;
    private final Map<String, QueueRank> queueRanks;
    private final Set<String> queueRankNames;

    public QueueRankManager(Countach plugin) {
        this.plugin = plugin;
        this.queueRanks = new HashMap<>();
        this.queueRankNames = new HashSet<>();
        this.loadOrRefresh();
    }

    public boolean exists(String name) {
        return queueRanks.containsKey(name);
    }

    public QueueRank getQueueRank(String name) {
        return queueRanks.get(name);
    }

    public QueueRank getQueueRankByRank(UUID uuid) {
        PlayerData playerData = AquaCoreAPI.INSTANCE.getPlayerData(uuid);
        return queueRanks.get(playerData.getHighestRank().getName());
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
