package dev.risas.countach.tablist.player.impl;

import dev.risas.countach.tablist.player.module.IPlayerVersion;
import dev.risas.countach.tablist.player.module.PlayerVersion;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerVersion1_7 implements IPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion());
    }
}
