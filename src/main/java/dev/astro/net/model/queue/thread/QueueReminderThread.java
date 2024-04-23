package dev.astro.net.model.queue.thread;

import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.queue.Queue;
import dev.astro.net.model.queue.QueueManager;
import dev.astro.net.utilities.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class QueueReminderThread extends Thread {

    private final Comet plugin;
    private final FileConfig languageFile;
    private final QueueManager queueManager;

    public QueueReminderThread(Comet plugin) {
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
        this.queueManager = plugin.getQueueManager();
    }

    @Override
    public void run() {
        while (true) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Queue queue = queueManager.getQueueByPlayer(player);

                if (queue != null) {
                    for (String message : languageFile.getStringList("queue-message.position")) {
                        ChatUtil.sendMessage(player, message
                                .replace("<queue>", queue.getServer())
                                .replace("<queue-player-position>", String.valueOf(queueManager.getQueuePlayerPosition(queue, player.getUniqueId())))
                                .replace("<queue-player-size>", String.valueOf(queue.getPlayers().size())));
                    }
                }
            }

            try {
                Thread.sleep(1000L * plugin.getQueueFile().getLong("queue.position-message-thread"));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
