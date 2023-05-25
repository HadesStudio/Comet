package dev.rage.comet.model.rank.impl;

import dev.rage.comet.model.rank.IRank;

import java.util.UUID;

public class Default implements IRank {

    @Override
    public String getRankName(UUID uuid) {
        return "";
    }
}
