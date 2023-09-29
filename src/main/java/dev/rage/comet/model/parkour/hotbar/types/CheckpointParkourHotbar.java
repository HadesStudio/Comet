package dev.rage.comet.model.parkour.hotbar.types;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.parkour.ParkourPlayer;
import dev.rage.comet.model.parkour.hotbar.ParkourHotbar;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.entity.Player;

public class CheckpointParkourHotbar extends ParkourHotbar {

    private final Comet plugin;
    private final FileConfig languageFile;

    public CheckpointParkourHotbar(Comet plugin) {
        super(new ItemBuilder(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial())
                .setName("&aLast Checkpoint")
                .setLore("&7Click to teleport to your last checkpoint.")
                .build(), 3);
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
    }

    @Override
    public void onItemInteract(Player player) {
        ParkourPlayer parkourPlayer = plugin.getParkourManager().getParkourPlayer(player);

        if (parkourPlayer.getCheckpoints().isEmpty()) {
            ChatUtil.sendMessage(player, languageFile.getString("parkour-message.no-checkpoint"));
            return;
        }

        player.teleport(parkourPlayer.getLastCheckpoint());
        ChatUtil.sendMessage(player, languageFile.getString("parkour-message.last-checkpoint"));
    }
}
