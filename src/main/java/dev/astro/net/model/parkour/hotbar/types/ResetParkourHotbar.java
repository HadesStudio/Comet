package dev.astro.net.model.parkour.hotbar.types;

import com.cryptomorin.xseries.XMaterial;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.parkour.hotbar.ParkourHotbar;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.item.ItemBuilder;
import org.bukkit.entity.Player;

public class ResetParkourHotbar extends ParkourHotbar {

    private final Comet plugin;
    private final FileConfig languageFile;

    public ResetParkourHotbar(Comet plugin) {
        super(new ItemBuilder(XMaterial.OAK_DOOR.parseMaterial())
                .setName("&cReset Parkour")
                .setLore("&7Click to reset your parkour progress.")
                .build(), 4);
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
    }

    @Override
    public void onItemInteract(Player player) {
        plugin.getParkourManager().resetParkourPlayer(player);
        ChatUtil.sendMessage(player, languageFile.getString("parkour-message.reset"));
    }
}
