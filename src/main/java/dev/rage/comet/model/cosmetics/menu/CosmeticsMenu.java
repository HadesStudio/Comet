package dev.rage.comet.model.cosmetics.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.menu.buttons.BalloonsButton;
import dev.rage.comet.model.cosmetics.menu.buttons.BannersButton;
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
    public int size(Map<Integer, Button> buttons) {
        return 9*4;
    }

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate("&6&lCosmetics");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(2, 1), new BannersButton(plugin.getBannersManager()));
        buttons.put(getSlot(3, 2), new BalloonsButton(plugin.getBalloonsManager()));

        return buttons;
    }
}
