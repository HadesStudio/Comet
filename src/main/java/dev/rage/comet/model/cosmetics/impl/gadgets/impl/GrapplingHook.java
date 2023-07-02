package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class GrapplingHook extends Gadget {

    private final double multiply, yBoost;

    public GrapplingHook(Comet plugin) {
        super(plugin);

        double gHookBoost = plugin.getGadgetsFile().getDouble("grappling-hook.boost");
        this.multiply = gHookBoost * 2.0;
        this.yBoost = gHookBoost * 0.75;
    }

    @Override
    public String getName() {
        return "Grappling Hook";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.FISHING_ROD.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("menu.name_format")
                        .replace("<name>", getName()))
                .setLore(plugin.getGadgetsFile().getStringList("menu.grappling_hook_lore"))
                .build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.FISHING_ROD.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("grappling-hook.item-name"))
                .setLore(plugin.getGadgetsFile().getStringList("grappling-hook.item-lore"))
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
                    .normalize().multiply(multiply).setY(yBoost));
        }
    }
}
