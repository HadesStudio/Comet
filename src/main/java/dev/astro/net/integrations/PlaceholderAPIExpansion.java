package dev.astro.net.integrations;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.leaderboard.LeaderboardManager;
import dev.astro.net.model.leaderboard.types.UserLeaderboardType;
import dev.astro.net.model.queue.Queue;
import dev.astro.net.model.queue.QueueManager;
import dev.astro.net.utilities.JavaUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final Comet plugin;
    private final QueueManager queueManager;
    private final LeaderboardManager leaderboardManager;

    public PlaceholderAPIExpansion(Comet plugin) {
        this.plugin = plugin;
        this.queueManager = plugin.getQueueManager();
        this.leaderboardManager = plugin.getLeaderboardManager();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return CometPlugin.getPlugin().getDescription().getAuthors().toString()
                .replace("[", "").replace("]", "");
    }

    @Override
    public @NotNull String getIdentifier() {
        return "comet";
    }

    @Override
    public @NotNull String getVersion() {
        return CometPlugin.getPlugin().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) return "";

        for (Queue queue : queueManager.getQueues().values()) {
            if (identifier.equalsIgnoreCase("queue_" + queue.getServer() + "_name")) {
                return queue.getServer();
            }
            if (identifier.equalsIgnoreCase("queue_" + queue.getServer() + "_size")) {
                return String.valueOf(queue.getPlayers().size());
            }
        }

        if (identifier.equals("queue_player_name")) {
            Queue queue = queueManager.getQueueByPlayer(player);
            return queue == null ? "None" : queue.getServer();
        }
        if (identifier.equals("queue_player_position")) {
            Queue queue = queueManager.getQueueByPlayer(player);
            return queue == null ? "0" : String.valueOf(queueManager.getQueuePlayerPosition(queue, player.getUniqueId()));
        }
        if (identifier.equals("queue_player_size")) {
            Queue queue = queueManager.getQueueByPlayer(player);
            return queue == null ? "0" : String.valueOf(queue.getPlayers().size());
        }

        Map<String, Long> parkourMap = leaderboardManager.getSortedUserLeaderboard(UserLeaderboardType.PARKOUR_TIME);

        if (identifier.contains("parkour_1_name")) {
            try {
                String name = parkourMap.keySet().toArray()[0].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_2_name")) {
            try {
                String name = parkourMap.keySet().toArray()[1].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_3_name")) {
            try {
                String name = parkourMap.keySet().toArray()[2].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_4_name")) {
            try {
                String name = parkourMap.keySet().toArray()[3].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_5_name")) {
            try {
                String name = parkourMap.keySet().toArray()[4].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_6_name")) {
            try {
                String name = parkourMap.keySet().toArray()[5].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_7_name")) {
            try {
                String name = parkourMap.keySet().toArray()[6].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_8_name")) {
            try {
                String name = parkourMap.keySet().toArray()[7].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_9_name")) {
            try {
                String name = parkourMap.keySet().toArray()[8].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_10_name")) {
            try {
                String name = parkourMap.keySet().toArray()[9].toString();
                if (name == null || name.isEmpty()) return " ";
                return name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }

        if (identifier.contains("parkour_1_value")) {
            try {
                String name = parkourMap.keySet().toArray()[0].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_2_value")) {
            try {
                String name = parkourMap.keySet().toArray()[1].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_3_value")) {
            try {
                String name = parkourMap.keySet().toArray()[2].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_4_value")) {
            try {
                String name = parkourMap.keySet().toArray()[3].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_5_value")) {
            try {
                String name = parkourMap.keySet().toArray()[4].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_6_value")) {
            try {
                String name = parkourMap.keySet().toArray()[5].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_7_value")) {
            try {
                String name = parkourMap.keySet().toArray()[6].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_8_value")) {
            try {
                String name = parkourMap.keySet().toArray()[7].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_9_value")) {
            try {
                String name = parkourMap.keySet().toArray()[8].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        if (identifier.contains("parkour_10_value")) {
            try {
                String name = parkourMap.keySet().toArray()[9].toString();
                if (name == null || name.isEmpty()) return " ";
                return JavaUtil.formatMillis(parkourMap.get(name));
            } catch (ArrayIndexOutOfBoundsException e) {
                return " ";
            }
        }
        return null;
    }
}
