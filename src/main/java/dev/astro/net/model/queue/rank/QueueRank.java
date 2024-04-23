package dev.astro.net.model.queue.rank;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter @Setter
public class QueueRank implements Comparable {

    private String name;
    private int priority;

    @Override
    public int compareTo(Object o) {
        int result = 0;

        if (o instanceof QueueRank) {
            result = this.priority - ((QueueRank) o).priority;
        }

        return result;
    }
}
