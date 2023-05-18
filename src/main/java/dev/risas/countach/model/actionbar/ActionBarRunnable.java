package dev.risas.countach.model.actionbar;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import dev.risas.countach.Countach;
import dev.risas.countach.utilities.ChatUtil;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * Created by Risas
 * Project: Countach
 * Date: 18-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class ActionBarRunnable implements Runnable {

    private final Countach plugin;
    private final int interval;
    private int index;

    public ActionBarRunnable(Countach plugin, int interval) {
        this.interval = interval;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            List<String> messages = plugin.getConfigFile().getStringList("action-bar.messages");
            if (index == messages.size()) index = 0;

            ActionBarAPI.sendActionBarToAllPlayers(plugin, ChatUtil.translate(messages.get(index)), interval * 20);
            index++;
        }
    }
}
