package dev.astro.net.model.selector.menu;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.selector.ServerSelector;
import dev.astro.net.model.selector.SubServerSelector;
import dev.astro.net.model.selector.menu.button.SubServerSelectorButton;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.Menu;
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
