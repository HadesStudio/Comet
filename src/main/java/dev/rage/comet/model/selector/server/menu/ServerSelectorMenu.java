package dev.rage.comet.model.selector.server.menu;

import dev.rage.comet.Comet;
import dev.rage.comet.model.selector.server.ServerSelector;
import dev.rage.comet.model.selector.server.ServerSelectorManager;
import dev.rage.comet.model.selector.server.menu.button.ServerSelectorButton;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerSelectorMenu extends Menu {

    private final Comet plugin;
    private final ServerSelectorManager serverSelectorManager;

    public ServerSelectorMenu(Comet plugin) {
        this.plugin = plugin;
        this.serverSelectorManager = plugin.getServerSelectorManager();
    }

    @Override
    public String getTitle(Player player) {
        return "Server Selector";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (ServerSelector serverSelector : serverSelectorManager.getServerSelectors()) {
            buttons.put(serverSelector.getIconSlot(), new ServerSelectorButton(plugin, serverSelector));
        }

        return buttons;
    }
}
