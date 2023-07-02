package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.JavaUtil;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class TeleportBow extends Gadget {

    private final long cooldown;

    public TeleportBow(Comet plugin) {
        super(plugin);

        this.cooldown = JavaUtil.formatLong(plugin.getGadgetsFile().getString("teleport-bow.cooldown"));
    }

    @Override
    public String getName() {
        return "Bow Teleport";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.BOW.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("menu.name_format")
                        .replace("<name>", getName()))
                .setLore(plugin.getGadgetsFile().getStringList("menu.teleport_bow_lore"))
                .build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.BOW.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("teleport-bow.item-name"))
                .setLore(plugin.getGadgetsFile().getStringList("teleport-bow.item-lore"))
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .build();
    }

    @Override
    public void onUsage(Player player) {
        player.getInventory().setItem(1, getItem());
        player.getInventory().setItem(9, new ItemStack(com.cryptomorin.xseries.XMaterial.ARROW.parseMaterial(), 1));
    }

    @Override
    public void onDisable() {

    }

    private void refund(Player player) {
        player.getInventory().getItemInHand().setDurability((short) 0);
        player.getInventory().setItem(9, new ItemStack(com.cryptomorin.xseries.XMaterial.ARROW.parseMaterial(), 1));
    }

    @EventHandler
    public void onProjectileThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Arrow) {
            Player player = (Player) event.getEntity().getShooter();

            if (hasCooldown(player.getUniqueId())) {
                event.setCancelled(true);

                plugin.getServer().getScheduler().runTaskLater(plugin, () -> refund(player), 2L);

                for (String s : plugin.getGadgetsFile().getStringList("teleport-bow.still-cooldown")) {
                    player.sendMessage(ChatUtil.translate(s
                            .replace("<time>", String.valueOf(getCooldown(player.getUniqueId())))));
                }
                return;
            }

            if (isItem(player.getItemInHand(), getItem())) {
                setCooldown(player.getUniqueId(), cooldown);

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

                //event.getEntity().removeMetadata(plugin.getName(), plugin);

                event.getEntity().remove();
            }
        }
    }
}
