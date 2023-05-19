package dev.rage.comet.model.cosmetics.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.Map;

public class CosmeticsMenu extends Menu {

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9*4;
    }

    @Override
    public String getTitle(Player player) {
        return "null";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        return buttons;
    }
}
