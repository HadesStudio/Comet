package dev.risas.countach.model.lazarus;

import java.util.UUID;

/**
 * Created by Risas
 * Project: Countach
 * Date: 20-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public interface ILazarusUserData {

    int getKills(UUID uuid);
    int getDeaths(UUID uuid);
    int getKillStreak(UUID uuid);
    int getLives(UUID uuid);
    String getDeathban(UUID uuid);
    boolean isConnected();
}
