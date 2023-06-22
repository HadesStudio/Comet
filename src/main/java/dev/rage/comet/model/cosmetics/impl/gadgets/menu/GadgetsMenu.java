package dev.rage.comet.model.cosmetics.impl.gadgets.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.model.cosmetics.impl.gadgets.GadgetsManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class GadgetsMenu extends Menu {

    private final Comet plugin;

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate("&aGadgets");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        int i = 0, j = 0;

        for (Gadget gadget : plugin.getGadgetsManager().getGadgets()) {
            if (i++ == 3) {
                i = 5;
            }
            else if (i == 8) {
                i = 1;
                j++;
            }

            buttons.put(getSlot(i, j), Button.fromItem(gadget.getIcon(), player1 -> {
                plugin.getNormalHotbarManager().giveHotbar(player);
                plugin.getCustomHotbarManager().giveHotbar(player);
                gadget.onUsage(player1);
            }));
        }

        return buttons;
    }
}
