package dev.astro.net.model.parkour;

import com.cryptomorin.xseries.XMaterial;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.hotbar.custom.CustomHotbarManager;
import dev.astro.net.model.hotbar.normal.NormalHotbarManager;
import dev.astro.net.model.parkour.hotbar.ParkourHotbarManager;
import dev.astro.net.model.spawn.SpawnManager;
import dev.astro.net.utilities.BukkitUtil;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class ParkourManager {

    private final FileConfig configFile;

    private final Map<UUID, ParkourPlayer> parkourPlayers;
    private final Set<Location> checkpoints = new HashSet<>();
    private final ItemStack checkpointItem, endLocationItem;

    private final ParkourHotbarManager parkourHotbarManager;
    private final NormalHotbarManager normalHotbarManager;
    private final CustomHotbarManager customHotbarManager;
    private final SpawnManager spawnManager;

    private Location startLocation, endLocation;

    public ParkourManager(Comet plugin) {
        this.configFile = plugin.getConfigFile();
        this.parkourPlayers = new HashMap<>();

        for (String s : configFile.getStringList("parkour.checkpoints"))
            checkpoints.add(BukkitUtil.deserializeLocation(s));

        this.startLocation = BukkitUtil.deserializeLocation(configFile.getString("parkour.start-location"));
        this.endLocation = BukkitUtil.deserializeBlockLocation(configFile.getString("parkour.end-location"));
        this.checkpointItem = new ItemBuilder(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial())
                .setName("&aCheckpoint")
                .setLore("&7Place this to create a checkpoint!")
                .build();
        this.endLocationItem = new ItemBuilder(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE.parseMaterial())
                .setName("&aEnd Location")
                .setLore("&7Place this to create a end location!")
                .build();
        this.parkourHotbarManager = plugin.getParkourHotbarManager();
        this.normalHotbarManager = plugin.getNormalHotbarManager();
        this.customHotbarManager = plugin.getCustomHotbarManager();
        this.spawnManager = plugin.getSpawnManager();
    }

    public ParkourPlayer getParkourPlayer(Player player) {
        return this.parkourPlayers.get(player.getUniqueId());
    }

    public void addParkourPlayer(Player player) {
        if (startLocation == null) {
            player.sendMessage(ChatUtil.translate("&cParkour start location not found."));
            return;
        }

        this.parkourPlayers.put(player.getUniqueId(), new ParkourPlayer(player));
        parkourHotbarManager.giveHotbar(player);

        player.teleport(startLocation);
        player.setWalkSpeed(0.2F);
    }

    public void removeParkourPlayer(Player player) {
        this.parkourPlayers.remove(player.getUniqueId());
        normalHotbarManager.giveHotbar(player);
        customHotbarManager.giveHotbar(player);
        spawnManager.toSpawn(player);
    }

    public boolean isParkourPlayer(Player player) {
        return this.parkourPlayers.containsKey(player.getUniqueId());
    }

    public void resetParkourPlayer(Player player) {
        ParkourPlayer parkourPlayer = getParkourPlayer(player);
        parkourPlayer.reset();
        player.teleport(startLocation);
    }

    public void addCheckpoint(Location location) {
        this.checkpoints.add(location);
        configFile.getConfiguration().set("parkour.checkpoints", BukkitUtil.serializeLocations(checkpoints));
        configFile.save();
    }

    public void removeCheckpoint(Location location) {
        this.checkpoints.remove(location);
        configFile.getConfiguration().set("parkour.checkpoints", BukkitUtil.serializeLocations(checkpoints));
        configFile.save();
    }

    public boolean isCheckpoint(Location location) {
        return this.checkpoints.contains(location);
    }

    public void setStartLocation(Location location) {
        this.startLocation = location;
        configFile.getConfiguration().set("parkour.start-location", BukkitUtil.serializeLocation(location));
        configFile.save();
    }

    public void setEndLocation(Location location) {
        this.endLocation = location;
        configFile.getConfiguration().set("parkour.end-location", BukkitUtil.serializeBlockLocation(location));
        configFile.save();
    }

    public boolean isFinishLocation(Location location) {
        return this.endLocation.equals(location);
    }
}
