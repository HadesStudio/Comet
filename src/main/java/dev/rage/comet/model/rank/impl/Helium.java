package dev.rage.comet.model.rank.impl;

import dev.rage.comet.model.rank.IRank;
import dev.rage.helium.api.HeliumAPI;

import java.util.UUID;

public class Helium implements IRank {

    @Override
    public String getRankName(UUID uuid) {
        return HeliumAPI.INSTANCE.getRankName(uuid);
    }
}
