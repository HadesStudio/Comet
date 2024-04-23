package dev.astro.net.listeners;

import dev.astro.net.model.pvpmode.PvpModeManager;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.PlayerUtil;
import dev.astro.net.utilities.Comet;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class PlayerListener implements Listener {

	private final Comet plugin;
	private final PvpModeManager pvpModeManager;

	public PlayerListener(Comet plugin) {
		this.plugin = plugin;
		this.pvpModeManager = plugin.getPvpModeManager();
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
		if (pvpModeManager.contains((Player) event.getWhoClicked())) {
			return;
		}

		event.setCancelled(!event.getWhoClicked().hasMetadata("comet-build-mode"));
	}

	@EventHandler
	private void onPlayerDoubleJump(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();

		if (plugin.getConfigFile().getBoolean("double-jump.enabled")) {
			if (plugin.getParkourManager().isParkourPlayer(player) || player.getGameMode() == GameMode.CREATIVE) {
				event.setCancelled(true);
				return;
			}

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
			if (plugin.getParkourManager().isParkourPlayer(player) || player.getGameMode() == GameMode.CREATIVE) {
				player.setAllowFlight(false);
				return;
			}
			if (!player.getAllowFlight()) {
				player.setAllowFlight(true);
			}
		}
	}

	@EventHandler
	private void onPlayerDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			if (!pvpModeManager.contains((Player) entity)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	private void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();

		if (entity instanceof Player && damager instanceof Player) {
			if (!pvpModeManager.contains((Player) damager) || !pvpModeManager.contains((Player) entity)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		event.setDroppedExp(0);
		event.getDrops().clear();

		Player victim = event.getEntity();

		if (pvpModeManager.contains(victim)) {
			Player killer = victim.getKiller();

			if (killer == null) return;

			if (pvpModeManager.contains(killer)) {
				ChatUtil.broadcast(plugin.getLanguageFile().getString("pvpmode-message.kill")
						.replace("<killer>", killer.getName())
						.replace("<victim>", victim.getName()));
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		CompletableFuture.runAsync(() -> {
			if (pvpModeManager.contains(player)) {
				pvpModeManager.preparePlayerForPvp(player);
			}
		});
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		pvpModeManager.removePlayer(event.getPlayer(), false);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!pvpModeManager.contains(event.getPlayer())) {
			return;
		}

		if (!event.hasItem()) {
			return;
		}

		ItemStack itemStack = event.getItem();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (itemStack.getType() == Material.POTION) {
				event.setCancelled(false);
			} else if (itemStack.getType() == Material.GOLDEN_APPLE) {
				event.setCancelled(false);
			}
		}
	}
}
