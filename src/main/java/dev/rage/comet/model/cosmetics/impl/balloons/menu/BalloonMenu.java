package dev.rage.comet.model.cosmetics.impl.balloons.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonHead;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonManager;
import dev.rage.comet.model.cosmetics.impl.balloons.menu.button.BalloonButton;
import dev.rage.comet.model.cosmetics.impl.balloons.menu.button.RemoveBalloonButton;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class BalloonMenu extends Menu {

    private final Comet plugin;
    private final BalloonManager balloonsManager;
    private final UserManager userManager;

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate("Balloons");
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (BalloonHead balloonHead : balloonsManager.getBalloonHeads().values()) {
            buttons.put(buttons.size(), new BalloonButton(plugin, balloonHead, userManager));
        }

        buttons.put(getSize() - 5, new RemoveBalloonButton(plugin, userManager));
        return buttons;
    }
}
