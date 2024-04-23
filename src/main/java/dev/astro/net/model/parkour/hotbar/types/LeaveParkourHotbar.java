package dev.astro.net.model.parkour.hotbar.types;

import com.cryptomorin.xseries.XMaterial;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.parkour.hotbar.ParkourHotbar;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.item.ItemBuilder;
import org.bukkit.entity.Player;

public class LeaveParkourHotbar extends ParkourHotbar {

    private final Comet plugin;
    private final FileConfig languageFile;

    public LeaveParkourHotbar(Comet plugin) {
        super(new ItemBuilder(XMaterial.RED_BED.parseMaterial())
                .setName("&cLeave Parkour")
                .setLore("&7Click to leave the parkour.")
                .build(), 5);
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
    }

    @Override
    public void onItemInteract(Player player) {
        plugin.getParkourManager().removeParkourPlayer(player);
        ChatUtil.sendMessage(player, languageFile.getString("parkour-message.leave"));
    }
}
