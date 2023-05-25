package dev.rage.comet.model.rank;

import dev.rage.comet.model.rank.impl.AquaCore;
import dev.rage.comet.model.rank.impl.Default;
import dev.rage.comet.model.rank.impl.LuckPerms;
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
        else {
            this.rank = new Default();
        }
    }
}


