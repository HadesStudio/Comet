package dev.rage.comet.scoreboard;

import dev.rage.comet.Comet;
import dev.rage.comet.scoreboard.animation.FastBoardFooterAnimation;
import dev.rage.comet.scoreboard.animation.FastBoardTitleAnimation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class FastBoardManager {

    private final Comet plugin;
    private final Map<UUID, FastBoard> boards;
    private FastBoardAdapter adapter;
    private FastBoardThread thread;
    private FastBoardListener listener;
    private FastBoardTitleAnimation titleAnimation;
    private FastBoardFooterAnimation footerAnimation;

    public FastBoardManager(Comet plugin) {
        this.plugin = plugin;
        this.boards = new HashMap<>();
        this.setup();
    }

    public void createBoardPlayer(Player player) {
        FastBoard board = new FastBoard(player);
        boards.put(player.getUniqueId(), board);
    }

    public void removeBoardPlayer(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) board.delete();
    }

    public void resetBoardAnimation() {
        FastBoardTitleAnimation animation = this.titleAnimation;
        if (animation != null) animation.cancel();
        this.titleAnimation = new FastBoardTitleAnimation(plugin);

        FastBoardFooterAnimation animation2 = this.footerAnimation;
        if (animation2 != null) animation2.cancel();
        this.footerAnimation = new FastBoardFooterAnimation(plugin);
    }

    public void setup() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }

        this.thread = new FastBoardThread(this);
        this.resetBoardAnimation();
    }

    public void onDisable() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }
}
