package dev.rage.comet.model.cosmetics.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.menu.buttons.BalloonButton;
import dev.rage.comet.model.cosmetics.menu.buttons.BannerButton;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class CosmeticsMenu extends Menu {

    private final Comet plugin;

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate("Cosmetics");
    }

    @Override
    public int getSize() {
        return 9 * 4;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(2, 1), new BannerButton(plugin.getBannerManager(), plugin.getUserManager()));
        buttons.put(getSlot(3, 2), new BalloonButton(plugin.getBalloonsManager()));

        return buttons;
    }
}
