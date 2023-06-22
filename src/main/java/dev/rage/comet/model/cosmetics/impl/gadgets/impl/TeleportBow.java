package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Sets;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.concurrent.TimeUnit;

public class TeleportBow extends Gadget {


    public TeleportBow(Comet plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Bow Teleport";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(XMaterial.BOW.parseMaterial())
                .setName(ChatColor.GREEN + getName())
                .setLore("",
                        ChatColor.GRAY + "Right click to use this gadget!"
                ).build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(XMaterial.BOW.parseMaterial())
                .setName(ChatColor.GREEN + getName())
                .setLore("",
                        ChatColor.GRAY + "Hold Right Click to lunch arrow!"
                )
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
    }

    @Override
    public void onUsage(Player player) {
        player.getInventory().setItem(1, getItem());
        player.getInventory().setItem(9, new ItemStack(XMaterial.ARROW.parseMaterial(), 1));
    }

    @Override
    public void onDisable() {

    }

    private void refund(Player player) {
        player.getInventory().getItemInHand().setDurability((short) 0);
        player.getInventory().setItem(9, new ItemStack(XMaterial.ARROW.parseMaterial(), 1));
    }

    @EventHandler
    public void onProjectileThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Arrow) {
            Player player = (Player) event.getEntity().getShooter();

            if (hasCooldown(player.getUniqueId())) {
                refund(player);
                player.sendMessage(ChatColor.RED + "You are still on cooldown!");
                return;
            }

            if (isItem(player.getItemInHand(), getItem())) {
                setCooldown(player.getUniqueId(), TimeUnit.SECONDS.toMillis(3));

                event.getEntity().setMetadata(plugin.getName(), new FixedMetadataValue(plugin, true));

                plugin.getServer().getScheduler().runTaskLater(plugin, () -> refund(player), 2L);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (event.getEntity().hasMetadata(plugin.getName())) {
                Location location = event.getEntity().getLocation();
                location.setDirection(player.getLocation().getDirection());
                player.teleport(location);

                event.getEntity().removeMetadata(plugin.getName(), plugin);
            }
        }
    }
}
