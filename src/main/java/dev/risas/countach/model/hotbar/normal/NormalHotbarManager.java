package dev.risas.countach.model.hotbar.normal;

import dev.risas.countach.Countach;
import dev.risas.countach.model.hotbar.normal.impl.EnderButtHotbar;
import dev.risas.countach.utilities.file.FileConfig;
import dev.risas.countach.utilities.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Risas
 * Project: Countach
 * Date: 19-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class NormalHotbarManager {

    private final Countach plugin;
    private final Map<String, NormalHotbar> hotbarMap;

    public NormalHotbarManager(Countach plugin) {
        this.plugin = plugin;
        this.hotbarMap = new HashMap<>();
        this.loadOrRefresh(false);
    }

    public Collection<NormalHotbar> getHotbars() {
        return hotbarMap.values();
    }

    public NormalHotbar getHotbar(ItemStack item) {
        for (NormalHotbar hotbar : getHotbars()) {
            if (hotbar.isSimilar(item)) {
                return hotbar;
            }
        }
        return null;
    }

    public NormalHotbar registerHotbarFromConfig(NormalHotbar hotbar, ConfigurationSection section) {
        hotbar.setEnabled(section.getBoolean(hotbar.getName() + ".enabled"));
        hotbar.setItem(new ItemBuilder(section.getString(hotbar.getName() + ".item.material"))
                .setName(section.getString(hotbar.getName() + ".item.name"))
                .setLore(section.getStringList(hotbar.getName() + ".item.description"))
                .setData(section.getInt(hotbar.getName() + ".item.data"))
                .setSkullOwner(section.getString(hotbar.getName() + ".item.head"))
                .setEnchanted(section.getBoolean(hotbar.getName() + ".item.enchanted"))
                .build());
        hotbar.setItemSlot(section.getInt(hotbar.getName() + ".item.slot"));
        hotbar.setSkullOwner(section.getString(hotbar.getName() + ".item.head"));
        return hotbar;
    }

    public void registerHotbars(NormalHotbar... hotbars) {
        for (NormalHotbar hotbar : hotbars) {
            hotbarMap.put(hotbar.getName(), hotbar);
        }
    }

    public void giveHotbar(Player player) {
        player.getInventory().clear();

        for (NormalHotbar hotbar : getHotbars()) {
            if (hotbar.isEnabled()) player.getInventory().setItem(hotbar.getItemSlot(), hotbar.getItem());
        }
    }

    public void loadOrRefresh(boolean reload) {
        if (reload) hotbarMap.clear();

        FileConfig hotbarFile = plugin.getHotbarFile();
        ConfigurationSection section = hotbarFile.getConfiguration().getConfigurationSection("normal");

        registerHotbars(registerHotbarFromConfig(new EnderButtHotbar("ender-butt"), section));

        if (reload) Bukkit.getOnlinePlayers().forEach(this::giveHotbar);
    }
}
