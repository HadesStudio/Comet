package dev.rage.comet.model.hotbar.normal.types;

import dev.rage.comet.Comet;
import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.file.FileConfig;
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
