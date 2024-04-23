package dev.astro.net.model.hcfcore;

import java.util.UUID;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public interface IHCFCore {

    int getKills(UUID uuid);
    int getDeaths(UUID uuid);
    int getKillStreak(UUID uuid);
    int getLives(UUID uuid);
    String getDeathban(UUID uuid);
    boolean isConnected();
}
