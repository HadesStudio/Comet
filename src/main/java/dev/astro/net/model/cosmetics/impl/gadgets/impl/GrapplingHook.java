package dev.astro.net.model.cosmetics.impl.gadgets.impl;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.gadgets.Gadget;
import dev.astro.net.utilities.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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
    public void onRemoval(Player player) {
        player.getInventory().setItem(1, null);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        String state = event.getState().toString();
        if (!state.equals("REEL_IN") && !state.equals("IN_GROUND")) {
            return;
        }

        Player player = event.getPlayer();
        Location from = player.getLocation();
        FishHook hook = event.getHook();
        Location to = hook.getLocation();

        from.setY(from.getY() + 1.0D);
        player.teleport(from);
        double g = -0.08D;
        double d = to.distance(from);
        double v_x = (1.0D + 0.07D * d) * (to.getX() - from.getX()) / d;
        double v_y = (1.0D + 0.03D * d) * (to.getY() - from.getY()) / d - 0.5D * g * d;
        double v_z = (1.0D + 0.07D * d) * (to.getZ() - from.getZ()) / d;
        Vector v = player.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        player.setVelocity(v.multiply(multiply));
        //event.getPlayer().setVelocity(to.toVector().subtract(from.toVector())
        //      .normalize().multiply(multiply).setY(yBoost));
    }
}
