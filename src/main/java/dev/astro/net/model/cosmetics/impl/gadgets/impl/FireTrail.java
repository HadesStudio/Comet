package dev.astro.net.model.cosmetics.impl.gadgets.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.gadgets.Gadget;
import dev.astro.net.utilities.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FireTrail extends Gadget {

    private final Set<Player> players;
    private final Map<BlockState, Long> locations;

    public FireTrail(Comet plugin) {
        super(plugin);

        this.players = Sets.newConcurrentHashSet();
        this.locations = Maps.newHashMap();

        CometPlugin.getPlugin().getServer().getScheduler().runTaskTimer(CometPlugin.getPlugin(), () -> {
            for (Player player : players) {
                player.setFireTicks(0);
            }

            Map<BlockState, Long> locations = Maps.newHashMap(this.locations);

            for (BlockState entry : locations.keySet()) {
                if (locations.get(entry) < System.currentTimeMillis()) {
                    entry.update(true);
                    this.locations.remove(entry);
                }
            }
        }, 20L, 1L);
    }

    @Override
    public String getName() {
        return "Fire Trail";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.BLAZE_POWDER.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("menu.name_format")
                        .replace("<name>", getName()))
                .setLore(plugin.getGadgetsFile().getStringList("menu.fire_trail_lore"))
                .build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.BLAZE_POWDER.parseMaterial())
                .setName(ChatColor.GREEN + getName())
                .build();
    }

    @Override
    public void onUsage(Player player) {
        players.add(player);
    }

    @Override
    public void onRemoval(Player player) {
        players.remove(player);
    }

    @Override
    public void onDisable() {

    }

    private void apply(Location loc) {
        Block block = loc.getBlock();
        if (block.getType() == com.cryptomorin.xseries.XMaterial.AIR.parseMaterial()) {
            locations.put(block.getState(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));

            block.setType(com.cryptomorin.xseries.XMaterial.FIRE.parseMaterial());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        /*
            I am not so sure how fast/slow is the
            process of casting a double to int.
        */
        if (((int)to.getX()) == ((int)from.getX()) &&
                ((int)to.getY()) == ((int)from.getY()) &&
                ((int)to.getZ()) == ((int)from.getZ())) { // Optimize¿?
            return;
        }

        if (players.contains(event.getPlayer())) {
            apply(from);
        }
    }

    @EventHandler
    public void onFireSpread(BlockSpreadEvent event) {
        if (event.getSource().getType() == com.cryptomorin.xseries.XMaterial.FIRE.parseMaterial()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFromEvent(BlockBurnEvent event) {
        event.setCancelled(true);
    }
}
