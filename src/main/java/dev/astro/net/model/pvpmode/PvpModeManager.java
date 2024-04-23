package dev.astro.net.model.pvpmode;

import dev.astro.net.utilities.BukkitUtil;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.file.FileConfig;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class PvpModeManager {

    private final Comet comet;

    private final Set<UUID> ids;

    private final FileConfig pvpModeFile, languageFile;

    public PvpModeManager(@NotNull Comet plugin) {
        comet = plugin;
        ids = new LinkedHashSet<>();
        pvpModeFile = comet.getPvpModeFile();
        languageFile = comet.getLanguageFile();
    }

    public void addPlayer(Player player) {
        if (contains(player)) {
            ChatUtil.sendMessage(player, languageFile.getString("pvpmode-message.already"));
            return;
        }

        preparePlayerForPvp(player);

        ids.add(player.getUniqueId());
        ChatUtil.sendMessage(player, languageFile.getString("pvpmode-message.join"));
    }

    public void removePlayer(Player player, boolean message) {
        if (!contains(player) && message) {
            ChatUtil.sendMessage(player, languageFile.getString("pvpmode-message.not-in-pvp"));
            return;
        }

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.setWalkSpeed(0.5F);

        player.getActivePotionEffects()
                .forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        PlayerInventory inventory = player.getInventory();

        inventory.setArmorContents(null);
        inventory.clear();

        comet.getSpawnManager().toSpawn(player);
        comet.getCustomHotbarManager().giveHotbar(player);
        comet.getNormalHotbarManager().giveHotbar(player);

        player.updateInventory();
    
        ids.remove(player.getUniqueId());

        if (message) {
            ChatUtil.sendMessage(player, languageFile.getString("pvpmode-message.leave"));
        }
    }

    public boolean contains(@NotNull Player player) {
        return ids.contains(player.getUniqueId());
    }

    public void preparePlayerForPvp(@NotNull Player player) {
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);

        player.getActivePotionEffects()
                .forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        player.setWalkSpeed(0.2F);

        PlayerInventory inventory = player.getInventory();

        inventory.setArmorContents(null);
        inventory.clear();

        if (getArmorContent() != null) {
            inventory.setArmorContents(getArmorContent());
        }

        if (getContent() != null) {
            inventory.setContents(getContent());
        }

        Location location = BukkitUtil.deserializeLocation(pvpModeFile.getString("location"));

        if (location == null) {
            ChatUtil.sendMessage(player, languageFile.getString("pvpmode-message.location-not-found"));
        } else {
            player.teleport(location);
        }

        player.updateInventory();
    }

    public void setContent(@NotNull PlayerInventory inventory) {
        pvpModeFile.getConfiguration().set("armor-content", BukkitUtil.serializeItemStackArray(inventory.getArmorContents()));
        pvpModeFile.getConfiguration().set("content", BukkitUtil.serializeItemStackArray(inventory.getContents()));
        pvpModeFile.save();
        pvpModeFile.reload();
    }

    public void setLocation(Location location) {
        pvpModeFile.getConfiguration().set("location", BukkitUtil.serializeLocation(location));
        pvpModeFile.save();
    }

    private ItemStack[] getArmorContent() {
        return BukkitUtil.deserializeItemStackArray(pvpModeFile.getString("armor-content"));
    }

    private ItemStack[] getContent() {
        return BukkitUtil.deserializeItemStackArray(pvpModeFile.getString("content"));
    }
}
