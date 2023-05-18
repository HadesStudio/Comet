package dev.rage.comet.tablist.player.impl;

import dev.rage.comet.tablist.player.module.IPlayerVersion;
import dev.rage.comet.tablist.player.module.PlayerVersion;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;

public class PlayerVersionViaVersion implements IPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(Via.getAPI().getPlayerVersion(player.getUniqueId()));
    }
}
