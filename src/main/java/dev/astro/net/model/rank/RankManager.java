package dev.astro.net.model.rank;

import dev.astro.net.model.rank.impl.AquaCore;
import dev.astro.net.model.rank.impl.Default;
import dev.astro.net.model.rank.impl.Helium;
import dev.astro.net.model.rank.impl.LuckPerms;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

@Getter
public class RankManager {

    private final IRank rank;

    public RankManager() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        if (pluginManager.getPlugin("AquaCore") != null) {
            this.rank = new AquaCore();
        }
        else if (pluginManager.getPlugin("LuckPerms") != null) {
            this.rank = new LuckPerms();
        }
        else if (pluginManager.getPlugin("Helium") != null) {
            this.rank = new Helium();
        }
        else {
            this.rank = new Default();
        }
    }
}


