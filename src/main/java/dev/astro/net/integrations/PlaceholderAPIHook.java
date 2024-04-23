package dev.astro.net.integrations;

import dev.astro.net.utilities.Comet;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

/**
 * Created by Risas
 * Project: Comet
 * Date: 14-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class PlaceholderAPIHook {

    @Getter
    private boolean enabled;

    public void initialize(Comet plugin) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIExpansion papi = new PlaceholderAPIExpansion(plugin);
            if (!papi.isRegistered()) papi.register();
            enabled = true;
        }
    }
}
