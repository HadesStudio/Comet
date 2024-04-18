package dev.rage.comet.model.hotbar.normal.types;

import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.entity.Player;

public class PvpModeHotbar extends NormalHotbar {

    private final Comet plugin;

    public PvpModeHotbar(String name, Comet plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void onItemInteract(Player player) {
        plugin.getPvpModeManager().addPlayer(player);
    }
}
