package dev.rage.comet.user;

import dev.rage.comet.Comet;
import dev.rage.comet.utilities.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserListener implements Listener {

    private final UserManager userManager;

    public UserListener(Comet plugin) {
        this.userManager = plugin.getUserManager();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        userManager.createUser(event.getUniqueId(), event.getName());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onPlayerLogin(PlayerLoginEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());

        if (user == null) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(ChatUtil.translate("&cFailed in load your user, please join again."));
        }
    }

    @EventHandler
    private void onPlayerSaveProfile(PlayerQuitEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());

        if (user != null) {
            userManager.destroyUser(user);
        }
    }
}
