package dev.risas.countach.model.queue.player;

import java.util.Comparator;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueuePlayerComparator implements Comparator<QueuePlayer> {

    @Override
    public int compare(QueuePlayer firstPlayer, QueuePlayer secondPlayer) {
        return firstPlayer.compareTo(secondPlayer);
    }
}
