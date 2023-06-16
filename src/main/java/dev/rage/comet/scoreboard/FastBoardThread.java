package dev.rage.comet.scoreboard;

import dev.rage.comet.utilities.ChatUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class FastBoardThread extends Thread {

    private final FastBoardManager fastBoardManager;

    FastBoardThread(FastBoardManager fastBoardManager) {
        this.fastBoardManager = fastBoardManager;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            updateBoard();

            try {
                sleep(50L * 2L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateBoard() {
        try {
            for (FastBoard board : fastBoardManager.getBoards().values()) {
                if (board == null) continue;

                Player player = board.getPlayer();

                String title = ChatUtil.placeholder(player, fastBoardManager.getAdapter().getTitle(player));
                board.updateTitle(title);

                List<String> newLines = fastBoardManager.getAdapter().getLines(player);
                board.updateLines(newLines);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

