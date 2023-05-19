package dev.rage.comet.model.selector.menu;

import dev.rage.comet.Comet;
import dev.rage.comet.model.selector.ServerSelector;
import dev.rage.comet.model.selector.SubServerSelector;
import dev.rage.comet.model.selector.menu.button.SubServerSelectorButton;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SubServerSelectorMenu extends Menu {

    private final Comet plugin;
    private final ServerSelector serverSelector;

    public SubServerSelectorMenu(Comet plugin, ServerSelector serverSelector) {
        this.plugin = plugin;
        this.serverSelector = serverSelector;
    }

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate(serverSelector.getMenuTitle()
                .replace("<server>", serverSelector.getName()));
    }

    @Override
    public int getSize() {
        return 9 * serverSelector.getMenuRows();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (SubServerSelector subServerSelector : serverSelector.getSubServerSelectors()) {
            buttons.put(subServerSelector.getIconSlot(), new SubServerSelectorButton(plugin, subServerSelector));
        }

        return buttons;
    }
}
