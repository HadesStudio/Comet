package dev.astro.net.model.lunarclient;

import com.lunarclient.bukkitapi.LunarClientAPI;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import lombok.Setter;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: MysticHQ
 * Date: 23-01-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Setter
public class LunarClientRunnable implements Runnable {

    private final Comet plugin;

    public LunarClientRunnable(Comet plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : LunarClientAPI.getInstance().getPlayersRunningLunarClient()) {
            for (Player viewer : LunarClientAPI.getInstance().getPlayersRunningLunarClient()) {
                LunarClientAPI.getInstance().overrideNametag(player, ChatUtil.placeholder(player, plugin.getConfigFile().getStringList("lunar-client.nametag")), viewer);
            }
        }
    }
}
