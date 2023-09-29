package dev.rage.comet.listeners;

import dev.rage.comet.utilities.PlayerUtil;
import dev.rage.comet.utilities.Comet;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerListener implements Listener {

	private final Comet plugin;

	public PlayerListener(Comet plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setExp(0);
		player.setGameMode(GameMode.ADVENTURE);
		player.setWalkSpeed(0.5F);
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(!event.getPlayer().hasMetadata("comet-build-mode"));
	}

	@EventHandler
	private void onPlayerItemPickup(PlayerPickupItemEvent event) {
		event.setCancelled(!event.getPlayer().hasMetadata("comet-build-mode"));
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		event.setCancelled(!event.getWhoClicked().hasMetadata("comet-build-mode"));
	}

	@EventHandler
	private void onPlayerDoubleJump(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();

		if (plugin.getConfigFile().getBoolean("double-jump.enabled")) {
			if (player.getGameMode().equals(GameMode.CREATIVE) || player.isFlying()) return;

			event.setCancelled(true);
			player.setVelocity(player.getLocation().getDirection().multiply(plugin.getConfigFile().getDouble("double-jump.velocity")).setY(1));
			PlayerUtil.playSound(player, plugin.getConfigFile().getString("double-jump.sound"));
			PlayerUtil.playEffect(player, plugin.getConfigFile().getString("double-jump.effect"));
		}
	}

	@EventHandler
	private void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (plugin.getConfigFile().getBoolean("double-jump.enabled")) {
			if (player.getGameMode().equals(GameMode.CREATIVE)) return;
			if (!player.getAllowFlight()) player.setAllowFlight(true);
		}
	}

	@EventHandler
	private void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	private void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}
}
