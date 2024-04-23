package dev.astro.net.model.hotbar.normal.types;

import dev.astro.net.model.hotbar.normal.NormalHotbar;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.file.FileConfig;
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
