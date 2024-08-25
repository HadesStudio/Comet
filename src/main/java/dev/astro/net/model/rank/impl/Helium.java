package dev.astro.net.model.rank.impl;

import dev.astro.net.model.rank.IRank;
import services.plasma.helium.api.HeliumAPI;

import java.util.UUID;

public class Helium implements IRank {

    @Override
    public String getRankName(UUID uuid) {
        return HeliumAPI.INSTANCE.getRankName(uuid);
    }
}
