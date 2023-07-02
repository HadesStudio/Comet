package dev.rage.comet.model.hotbar.normal;

import dev.rage.comet.model.hotbar.normal.impl.CosmeticsHotbar;
import dev.rage.comet.Comet;
import dev.rage.comet.model.hotbar.normal.impl.EnderButtHotbar;
import dev.rage.comet.model.hotbar.normal.impl.HidePlayerHotbar;
import dev.rage.comet.model.hotbar.normal.impl.ServerSelectorHotbar;
import dev.rage.comet.model.hotbar.normal.impl.ShowPlayerHotbar;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Risas
 * Project: Comet
 * Date: 19-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class NormalHotbarManager {

    private final Comet plugin;
    private final Map<String, NormalHotbar> hotbarMap;

    public NormalHotbarManager(Comet plugin) {
        this.plugin = plugin;
        this.hotbarMap = new LinkedHashMap<>();
        this.loadOrRefresh(false);
    }

    public Collection<NormalHotbar> getHotbars() {
        return hotbarMap.values();
    }

    public NormalHotbar getHotbar(String name) {
        return hotbarMap.get(name);
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

        registerHotbars(
                registerHotbarFromConfig(new EnderButtHotbar("ender-butt", section.getBoolean("ender-butt.mount")), section),
                registerHotbarFromConfig(new ServerSelectorHotbar(plugin, "server-selector"), section),
                registerHotbarFromConfig(new ShowPlayerHotbar("show-players", this), section),
                registerHotbarFromConfig(new HidePlayerHotbar("hide-players", this), section),
                registerHotbarFromConfig(new CosmeticsHotbar("cosmetics", plugin), section)
        );

        if (reload) Bukkit.getOnlinePlayers().forEach(this::giveHotbar);
    }
}
