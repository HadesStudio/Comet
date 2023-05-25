package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

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
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .addEnchantment(Enchantment.DURABILITY, 9999)
                .build();
    }

    @Override
    public void onUsage(Player player) {
        player.getInventory().addItem(new ItemStack(XMaterial.ARROW.parseMaterial(), 1));
        player.getInventory().setItem(1, getItem());
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();

            if (isItem(player.getInventory().getItemInMainHand(), getItem())) {
                player.teleport(event.getEntity().getLocation());
            }
        }
    }
}
