package dev.astro.net.model.parkour.hotbar.types;

import com.cryptomorin.xseries.XMaterial;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.parkour.ParkourPlayer;
import dev.astro.net.model.parkour.hotbar.ParkourHotbar;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.item.ItemBuilder;
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
