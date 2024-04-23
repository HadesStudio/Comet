package dev.astro.net.model.rank.impl;

import dev.astro.net.model.rank.IRank;
import me.activated.core.api.player.PlayerData;
import me.activated.core.plugin.AquaCoreAPI;

import java.util.UUID;

public class AquaCore implements IRank {

    @Override
    public String getRankName(UUID uuid) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(uuid);
        return data == null ? "" : data.getHighestRank().getName();
    }
}
