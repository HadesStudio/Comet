package dev.astro.net.model.spawn;

import dev.astro.net.utilities.BukkitUtil;
import dev.astro.net.utilities.Comet;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 14-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class SpawnManager {

    private final Comet plugin;
    private Location location;

    public SpawnManager(Comet plugin) {
        this.plugin = plugin;
        this.location = BukkitUtil.deserializeLocation(plugin.getConfigFile().getString("spawn-location"));
    }

    public void setLocation(Location location) {
        this.location = location;
        plugin.getConfigFile().getConfiguration().set("spawn-location", BukkitUtil.serializeLocation(location));
        plugin.getConfigFile().save();
    }

    public void toSpawn(Player player) {
        player.teleport(location == null ? player.getWorld().getSpawnLocation() : location);
    }

    public void toSpawn(Entity entity) {
        entity.teleport(location == null ? entity.getWorld().getSpawnLocation() : location);
    }
}
