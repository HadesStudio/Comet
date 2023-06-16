package dev.rage.comet.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Risas
 * Project: Neron
 * Date: 30-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public interface FastBoardAdapter {

    String getTitle(Player player);
    List<String> getLines(Player player);
}
