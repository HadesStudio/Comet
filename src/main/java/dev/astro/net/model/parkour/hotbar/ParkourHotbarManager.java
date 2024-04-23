package dev.astro.net.model.parkour.hotbar;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.parkour.hotbar.types.CheckpointParkourHotbar;
import dev.astro.net.model.parkour.hotbar.types.LeaveParkourHotbar;
import dev.astro.net.model.parkour.hotbar.types.ResetParkourHotbar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Risas
 * Project: Comet
 * Date: 19-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class ParkourHotbarManager {

    private final Comet plugin;
    private final Map<ItemStack, ParkourHotbar> parkourHotbarMap;

    public ParkourHotbarManager(Comet plugin) {
        this.plugin = plugin;
        this.parkourHotbarMap = new LinkedHashMap<>();
        this.load();
    }

    public ParkourHotbar getParkourHotbar(ItemStack item) {
        return parkourHotbarMap.get(item);
    }

    public void registerHotbars(ParkourHotbar... hotbars) {
        for (ParkourHotbar parkourHotbar : hotbars) {
            parkourHotbarMap.put(parkourHotbar.getItem(), parkourHotbar);
        }
    }

    public void giveHotbar(Player player) {
        player.getInventory().clear();

        for (ParkourHotbar parkourHotbar : parkourHotbarMap.values()) {
            player.getInventory().setItem(parkourHotbar.getItemSlot(), parkourHotbar.getItem());
        }
    }

    public void load() {
        registerHotbars(
                new CheckpointParkourHotbar(plugin),
                new LeaveParkourHotbar(plugin),
                new ResetParkourHotbar(plugin)
        );
    }
}
