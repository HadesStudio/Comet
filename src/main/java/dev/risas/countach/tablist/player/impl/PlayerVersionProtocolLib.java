package dev.risas.countach.tablist.player.impl;

import com.comphenix.protocol.ProtocolLibrary;
import dev.risas.countach.tablist.player.module.IPlayerVersion;
import dev.risas.countach.tablist.player.module.PlayerVersion;
import org.bukkit.entity.Player;

public class PlayerVersionProtocolLib implements IPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(ProtocolLibrary.getProtocolManager().getProtocolVersion(player));
    }
}
