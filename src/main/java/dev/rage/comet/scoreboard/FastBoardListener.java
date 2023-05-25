package dev.rage.comet.scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Risas
 * Project: Neron
 * Date: 30-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class FastBoardListener implements Listener {

    private final FastBoardManager fastBoardManager;

    public FastBoardListener(FastBoardManager fastBoardManager) {
        this.fastBoardManager = fastBoardManager;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        fastBoardManager.createBoardPlayer(event.getPlayer());
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        fastBoardManager.removeBoardPlayer(event.getPlayer());
    }
}
