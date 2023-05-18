package dev.rage.comet.tablist.player;

import dev.rage.comet.tablist.player.impl.PlayerVersionProtocolLib;
import dev.rage.comet.tablist.player.impl.PlayerVersionViaVersion;
import dev.rage.comet.tablist.player.module.IPlayerVersion;
import dev.rage.comet.tablist.player.module.PlayerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class PlayerVersionManager {

    public static IPlayerVersion version;

    public PlayerVersionManager() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        if (pluginManager.getPlugin("ViaVersion") != null) {
            version = new PlayerVersionViaVersion();
        }
        else if (pluginManager.getPlugin("ProtocolLib") != null) {
            version = new PlayerVersionProtocolLib();
        }
    }

    public static PlayerVersion getPlayerVersion(Player player) {
        return version.getPlayerVersion(player);
    }
}
