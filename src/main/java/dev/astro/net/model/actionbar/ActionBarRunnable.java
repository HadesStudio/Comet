package dev.astro.net.model.actionbar;

import dev.astro.net.utilities.actionbar.ActionBarAPI;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * Created by Risas
 * Project: Comet
 * Date: 18-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class ActionBarRunnable implements Runnable {

    private final Comet plugin;
    private final int interval;
    private int index;

    public ActionBarRunnable(Comet plugin, int interval) {
        this.interval = interval;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            List<String> messages = plugin.getConfigFile().getStringList("action-bar.messages");
            if (index == messages.size()) index = 0;

            ActionBarAPI.sendActionBarToAllPlayers(ChatUtil.translate(messages.get(index)), interval * 20);
            index++;
        }
    }
}
