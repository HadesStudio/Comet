package dev.astro.net.model.queue.player;

import dev.astro.net.model.queue.rank.QueueRank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Created by Risas
 * Project: Comet
 * Date: 19-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter @Setter
public class QueuePlayer implements Comparable {

    private final UUID uuid;
    private QueueRank rank;
    private long inserted;

    public QueuePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public int compareTo(@NotNull Object o) {
        int result = 0;

        if (o instanceof QueuePlayer) {
            QueuePlayer otherPlayer = (QueuePlayer) o;
            result = this.rank.getPriority() - otherPlayer.getRank().getPriority();

            if (result == 0) {
                if (this.inserted < otherPlayer.getInserted()) return -1;
                else return 1;
            }
        }

        return result;
    }
}
