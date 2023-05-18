package dev.risas.countach.tablist.player;

import dev.risas.countach.tablist.player.impl.PlayerVersion1_7;
import dev.risas.countach.tablist.player.impl.PlayerVersionProtocolLib;
import dev.risas.countach.tablist.player.impl.PlayerVersionViaVersion;
import dev.risas.countach.tablist.player.module.IPlayerVersion;
import dev.risas.countach.tablist.player.module.PlayerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerVersionManager {

    public static IPlayerVersion version;

    public PlayerVersionManager() {
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (serverVersion.equalsIgnoreCase("v1_7_R4")) {
            version = new PlayerVersion1_7();
        }
        else if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
            version = new PlayerVersionViaVersion();
        }
        else if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            version = new PlayerVersionProtocolLib();
        }
    }

    public static PlayerVersion getPlayerVersion(Player player) {
        return version.getPlayerVersion(player);
    }
}
