package dev.astro.net.model.hotbar.normal.types;

import dev.astro.net.model.hotbar.normal.NormalHotbar;
import dev.astro.net.model.hotbar.normal.NormalHotbarManager;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class ShowPlayerHotbar extends NormalHotbar {

    private final NormalHotbarManager normalHotbarManager;

    public ShowPlayerHotbar(String name, NormalHotbarManager normalHotbarManager) {
        super(name);
        this.normalHotbarManager = normalHotbarManager;
    }

    @Override
    public void onItemInteract(Player player) {
        for (Player online : player.getServer().getOnlinePlayers()) {
            player.showPlayer(online);
        }

        NormalHotbar hidePlayerHotbar = normalHotbarManager.getHotbar("hide-players");
        player.getInventory().setItem(hidePlayerHotbar.getItemSlot(), hidePlayerHotbar.getItem());
    }
}
