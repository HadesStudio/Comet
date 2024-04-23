package dev.astro.net.model.rank.impl;

import dev.astro.net.model.rank.IRank;

import java.util.UUID;

public class Default implements IRank {

    @Override
    public String getRankName(UUID uuid) {
        return "";
    }
}
