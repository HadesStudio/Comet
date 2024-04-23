package dev.astro.net.model.leaderboard.types;

import lombok.Getter;

@Getter
public enum UserLeaderboardType {

    PARKOUR_TIME("Parkour Time");

    private final String name;

    UserLeaderboardType(String name) {
        this.name = name;
    }
}
