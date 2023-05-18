package dev.risas.countach.integrations;

import dev.risas.countach.Countach;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

/**
 * Created by Risas
 * Project: Countach
 * Date: 14-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class PlaceholderAPIHook {

    @Getter
    private boolean enabled;

    public void initialize(Countach plugin) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIExpansion papi = new PlaceholderAPIExpansion(plugin);
            if (!papi.isRegistered()) papi.register();
            enabled = true;
        }
    }
}
