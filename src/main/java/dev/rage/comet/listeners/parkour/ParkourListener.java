package dev.rage.comet.listeners.parkour;

import dev.rage.comet.Comet;
import dev.rage.comet.model.parkour.ParkourManager;
import dev.rage.comet.model.parkour.ParkourPlayer;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ParkourListener implements Listener {

    private final FileConfig languageFile;

    private final ParkourManager parkourManager;
    private final UserManager userManager;

    public ParkourListener(Comet plugin) {
        this.languageFile = plugin.getLanguageFile();
        this.parkourManager = plugin.getParkourManager();
        this.userManager = plugin.getUserManager();
    }

    @EventHandler
    private void onParkourCheckpointPlace(BlockPlaceEvent event) {
        if (!event.getItemInHand().isSimilar(parkourManager.getCheckpointItem())) return;
        parkourManager.addCheckpoint(event.getBlockPlaced().getLocation());
        ChatUtil.sendMessage(event.getPlayer(), "&aParkour checkpoint added!");
    }

    @EventHandler
    private void onParkourEndLocationPlace(BlockPlaceEvent event) {
        if (!event.getItemInHand().isSimilar(parkourManager.getEndLocationItem())) return;
        parkourManager.setEndLocation(event.getBlockPlaced().getLocation());
        ChatUtil.sendMessage(event.getPlayer(), "&aParkour end location set!");
    }

    @EventHandler
    private void onParkourCheckpointBreak(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        if (!parkourManager.isCheckpoint(location)) return;

        parkourManager.removeCheckpoint(location);
        ChatUtil.sendMessage(event.getPlayer(), "&aParkour checkpoint removed!");
    }

    @EventHandler
    private void onParkourMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ParkourPlayer parkourPlayer = parkourManager.getParkourPlayer(player);
        if (parkourPlayer == null) return;

        Location playerLocation = player.getLocation();
        Location blockLocation = new Location(playerLocation.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY(), playerLocation.getBlockZ());

        if (parkourManager.isFinishLocation(blockLocation)) {
            long elapsedTime = parkourPlayer.getElapsedTime();
            String elapsedTimeFormatted = parkourPlayer.getElapsedTimeFormatted();

            User user = userManager.getUser(player.getUniqueId());

            if (user.getParkourTime() > elapsedTime || user.getParkourTime() == 0) {
                user.setParkourTime(elapsedTime);
                ChatUtil.sendMessage(player, languageFile.getString("parkour-message.new-time")
                        .replace("<time>", elapsedTimeFormatted));
            }

            parkourManager.removeParkourPlayer(player);
            ChatUtil.sendMessage(player, languageFile.getString("parkour-message.completed")
                    .replace("<time>", elapsedTimeFormatted));
            return;
        }

        if (parkourManager.isCheckpoint(blockLocation) && !parkourPlayer.hasCheckpoint(blockLocation)) {
            parkourPlayer.addCheckpoint(blockLocation);
            ChatUtil.sendMessage(player, languageFile.getString("parkour-message.checkpoint"));
        }
    }

    @EventHandler
    private void onParkourPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ParkourPlayer parkourPlayer = parkourManager.getParkourPlayer(player);
        if (parkourPlayer == null) return;

        parkourManager.removeParkourPlayer(player);
    }
}
