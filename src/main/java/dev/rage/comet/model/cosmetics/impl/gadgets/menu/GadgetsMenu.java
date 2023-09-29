package dev.rage.comet.model.cosmetics.impl.gadgets.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.Map;

public class GadgetsMenu extends Menu {

    private final Comet plugin;
    private final UserManager userManager;

    public GadgetsMenu(Comet plugin) {
        this.plugin = plugin;
        this.userManager = plugin.getUserManager();
    }

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
                if (!gadget.hasPermission(player1)) {
                    ChatUtil.sendMessage(player1, plugin.getLanguageFile().getString("cosmetic-message.no-permission"));
                    return;
                }

                plugin.getNormalHotbarManager().giveHotbar(player1);
                plugin.getCustomHotbarManager().giveHotbar(player1);

                User user = userManager.getUser(player1.getUniqueId());
                user.applyGadget(player1, gadget);
            }));
        }

        return buttons;
    }
}
