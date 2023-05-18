package dev.risas.countach.model.hotbar.custom;

import dev.risas.countach.Countach;
import dev.risas.countach.utilities.file.FileConfig;
import dev.risas.countach.utilities.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Risas
 * Project: Countach
 * Date: 19-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class CustomHotbarManager {

    private final Countach plugin;
    private final Map<String, CustomHotbar> hotbarMap;
    private final Set<String> hotbarNames;

    public CustomHotbarManager(Countach plugin) {
        this.plugin = plugin;
        this.hotbarMap = new HashMap<>();
        this.hotbarNames = new HashSet<>();
        this.loadOrRefresh(false);
    }

    public Collection<CustomHotbar> getHotbars() {
        return hotbarMap.values();
    }

    public boolean exists(String name) {
        return hotbarMap.containsKey(name);
    }

    public CustomHotbar getHotbar(String name) {
        return hotbarMap.get(name);
    }

    public CustomHotbar getHotbar(ItemStack item) {
        for (CustomHotbar hotbar : getHotbars()) {
            if (hotbar.isSimilar(item)) {
                return hotbar;
            }
        }
        return null;
    }

    public void giveHotbar(Player player) {
        for (CustomHotbar hotbar : getHotbars()) {
            if (hotbar.isEnabled()) player.getInventory().setItem(hotbar.getItemSlot(), hotbar.getItem(player));
        }
    }

    public void loadOrCreate(String name, ConfigurationSection section) {
        CustomHotbar hotbar = exists(name) ? getHotbar(name) : new CustomHotbar();
        hotbar.setName(name);
        hotbar.setEnabled(section.getBoolean(name + ".enabled"));
        hotbar.setItem(new ItemBuilder(section.getString(name + ".item.material"))
                .setName(section.getString(name + ".item.name"))
                .setLore(section.getStringList(name + ".item.description"))
                .setData(section.getInt(name + ".item.data"))
                .setSkullOwner(section.getString(name + ".item.head"))
                .setEnchanted(section.getBoolean(name + ".item.enchanted"))
                .build());
        hotbar.setItemSlot(section.getInt(name+ ".item.slot"));
        hotbar.setSkullOwner(section.getString(name + ".item.head"));
        hotbar.setCommands(section.getStringList(name + ".commands"));

        if (!exists(name)) hotbarMap.put(name, hotbar);
    }

    public void loadOrRefresh(boolean reload) {
        FileConfig hotbarFile = plugin.getHotbarFile();
        ConfigurationSection section = hotbarFile.getConfiguration().getConfigurationSection("custom");

        for (String hotbarName : section.getKeys(false)) {
            loadOrCreate(hotbarName, section);
        }

        for (CustomHotbar hotbar : hotbarMap.values()) {
            String hotbarName = hotbar.getName();
            if (!section.contains(hotbarName)) hotbarNames.add(hotbarName);
        }

        hotbarNames.forEach(hotbarMap::remove);
        hotbarNames.clear();

        if (reload) Bukkit.getOnlinePlayers().forEach(this::giveHotbar);
    }
}
