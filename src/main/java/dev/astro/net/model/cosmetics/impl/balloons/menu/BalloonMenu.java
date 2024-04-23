package dev.astro.net.model.cosmetics.impl.balloons.menu;

import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.balloons.BalloonHead;
import dev.astro.net.model.cosmetics.impl.balloons.BalloonManager;
import dev.astro.net.model.cosmetics.impl.balloons.menu.button.BalloonButton;
import dev.astro.net.model.cosmetics.impl.balloons.menu.button.RemoveBalloonButton;
import dev.astro.net.user.UserManager;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.Menu;
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
