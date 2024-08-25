package dev.astro.net.user;

import dev.astro.net.CometPlugin;
import dev.astro.net.model.cosmetics.impl.outfits.entries.impl.CustomOutfit;
import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.CompletableFuture;

public class UserListener implements Listener {

    private final Comet plugin;
    private final UserManager userManager;

    public UserListener(Comet plugin) {
        this.plugin = plugin;
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
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = userManager.getUser(player.getUniqueId());

        if (user.getBalloon() != null) {
            user.applyBalloon(plugin, event.getPlayer(), user.getBalloon());
        }

        if (user.getMascot() != null) {
            user.applyMascot(plugin, event.getPlayer(), user.getMascot());
        }

        if (user.getOutfit() == null) {
            if (CometPlugin.get().getOutfitManager().getCustomOutfits().isEmpty()) return;

            for (CustomOutfit customOutfit : CometPlugin.get().getOutfitManager().getCustomOutfits()) {
                if (customOutfit.getName().equals(CometPlugin.get().getRankManager().getRank().getRankName(player.getUniqueId()))) {
                    user.setOutfit(customOutfit);
                    customOutfit.apply(CometPlugin.get(), player);
                }
            }
        } else {
            user.getOutfit().apply(plugin, player);
        }

        if (user.getBanner() != null) {
            user.applyBanner(event.getPlayer(), user.getBanner());
        }

        if (user.getParticle() != null) {
            plugin.getParticlesManager().apply(event.getPlayer(), user.getParticle());
        }
    }

    @EventHandler
    private void onPlayerSaveProfile(PlayerQuitEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());

        if (user != null) {
            if (user.hasBalloonModel()) user.getBalloonModel().remove();
            if (user.hasMascotModel()) user.getMascotModel().die();

            CompletableFuture.runAsync(() -> userManager.destroyUser(user));
        }
    }
}
