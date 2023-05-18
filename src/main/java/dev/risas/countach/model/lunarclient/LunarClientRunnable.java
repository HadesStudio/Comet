package dev.risas.countach.model.lunarclient;

import com.lunarclient.bukkitapi.LunarClientAPI;
import dev.risas.countach.Countach;
import dev.risas.countach.utilities.ChatUtil;
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

    private final Countach plugin;

    public LunarClientRunnable(Countach plugin) {
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
