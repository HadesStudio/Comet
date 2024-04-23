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

public class HidePlayerHotbar extends NormalHotbar {

    private final NormalHotbarManager normalHotbarManager;

    public HidePlayerHotbar(String name, NormalHotbarManager normalHotbarManager) {
        super(name);
        this.normalHotbarManager = normalHotbarManager;
    }

    @Override
    public void onItemInteract(Player player) {
        for (Player online : player.getServer().getOnlinePlayers()) {
            player.hidePlayer(online);
        }

        NormalHotbar showPlayerHotbar = normalHotbarManager.getHotbar("show-players");
        player.getInventory().setItem(showPlayerHotbar.getItemSlot(), showPlayerHotbar.getItem());
    }
}
