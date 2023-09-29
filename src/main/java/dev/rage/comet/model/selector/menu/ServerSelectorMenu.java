package dev.rage.comet.model.selector.menu;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.selector.ServerSelector;
import dev.rage.comet.model.selector.ServerSelectorManager;
import dev.rage.comet.model.selector.menu.button.ServerSelectorButton;
import dev.rage.comet.utilities.ChatUtil;
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
        return ChatUtil.translate(plugin.getServerSelectorFile().getString("menu.title"));
    }

    @Override
    public int getSize() {
        return 9 * plugin.getServerSelectorFile().getInt("menu.rows");
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
