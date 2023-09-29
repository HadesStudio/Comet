package dev.rage.comet.model.parkour.hotbar.types;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.parkour.hotbar.ParkourHotbar;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.item.ItemBuilder;
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
