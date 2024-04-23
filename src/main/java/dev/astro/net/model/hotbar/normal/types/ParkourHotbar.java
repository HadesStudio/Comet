package dev.astro.net.model.hotbar.normal.types;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.hotbar.normal.NormalHotbar;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.file.FileConfig;
import org.bukkit.entity.Player;

public class ParkourHotbar extends NormalHotbar {

    private final Comet plugin;
    private final FileConfig languageFile;

    public ParkourHotbar(String name, Comet plugin) {
        super(name);
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
    }

    @Override
    public void onItemInteract(Player player) {
        plugin.getParkourManager().addParkourPlayer(player);
        ChatUtil.sendMessage(player, languageFile.getString("parkour-message.join"));
    }
}
