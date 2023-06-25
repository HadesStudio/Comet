package dev.rage.comet.integrations;

import dev.rage.comet.Comet;
import dev.rage.comet.model.queue.Queue;
import dev.rage.comet.model.queue.QueueManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final Comet plugin;
    private final QueueManager queueManager;

    public PlaceholderAPIExpansion(Comet plugin) {
        this.plugin = plugin;
        this.queueManager = plugin.getQueueManager();
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
        return plugin.getDescription().getAuthors().toString()
                .replace("[", "").replace("]", "");
    }

    @Override
    public @NotNull String getIdentifier() {
        return "comet";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
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
        return null;
    }
}
