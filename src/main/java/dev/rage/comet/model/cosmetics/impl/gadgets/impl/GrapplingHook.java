package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class GrapplingHook extends Gadget {

    public GrapplingHook(Comet plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Grappling Hook";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(XMaterial.FISHING_ROD.parseMaterial())
                .setName(ChatColor.GREEN + getName())
                .setLore("",
                        ChatColor.GRAY + "Right click to use this gadget!")
                .build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(XMaterial.FISHING_ROD.parseMaterial())
                .setName(ChatColor.GREEN + getName())
                .setLore("",
                        ChatColor.GRAY + "Right click to use it!")
                .build();
    }

    @Override
    public void onUsage(Player player) {
        player.getInventory().setItem(1, getItem());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.IN_GROUND) {
            event.getPlayer().setVelocity(event.getHook().getLocation().toVector().subtract(event.getPlayer().getLocation().toVector())
                    .normalize().multiply(3.0).setY(0.4));
        }
    }
}
