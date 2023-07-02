package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class SnowBallHider extends Gadget {

    public SnowBallHider(Comet plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Snow Ball Hider";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.SNOWBALL.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("menu.name_format")
                        .replace("<name>", getName()))
                .setLore(plugin.getGadgetsFile().getStringList("menu.snowball_hider_lore"))
                .build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.SNOWBALL.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("snowball-hider.item-name"))
                .setLore(plugin.getGadgetsFile().getString("snowball-hider.item-lore"))
                .build();
    }

    /*
        I can use the same method "onUsage", but knowing
        the child laughs, he starts to cry for stupid things of "order".
    */
    private void refund(Player player) {
        player.getInventory().setItem(1, getItem());
    }

    @Override
    public void onUsage(Player player) {
        player.getInventory().setItem(1, getItem());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onProjectileThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player) || !(event.getEntity() instanceof Snowball))
            return;

        event.getEntity().setMetadata(plugin.getName() + getName(), new FixedMetadataValue(plugin, true));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> refund((Player) event.getEntity().getShooter()), 2L);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Snowball) || !(event.getEntity() instanceof Player))
            return;

        if (!event.getDamager().hasMetadata(plugin.getName() + getName()))
            return;

        Player shooter = (Player) ((Snowball) event.getDamager()).getShooter();

        event.setCancelled(true);

        shooter.hidePlayer((Player) event.getEntity());

        for (String s : plugin.getGadgetsFile().getStringList("snowball-hider.hit-message")) {
            shooter.sendMessage(ChatUtil.translate(s.replace("<player>", event.getEntity().getName())));
        }
    }
}
