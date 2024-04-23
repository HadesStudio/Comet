package dev.astro.net.model.cosmetics.impl.gadgets.impl;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.gadgets.Gadget;
import dev.astro.net.utilities.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Mount extends Gadget {

    public Mount(Comet plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Mount";
    }

    @Override
    public String getPermission() {
        return "comet.gadgets.mount";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.SADDLE.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("menu.name_format")
                        .replace("<name>", getName()))
                .setLore(plugin.getGadgetsFile().getStringList("menu.mount_lore"))
                .build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.SADDLE.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("mount.item-name"))
                .setLore(plugin.getGadgetsFile().getString("mount.item-lore"))
                .build();
    }

    @Override
    public void onUsage(Player player) {
        player.getInventory().setItem(1, getIcon());
    }

    @Override
    public void onRemoval(Player player) {
        player.getInventory().setItem(1, null);
    }

    @Override
    public void onDisable() {

    }


    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() == null || !(event.getRightClicked() instanceof Player)) return;

        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInHand();

        if (hand == null) return;

        if (isItem(hand, getIcon())) {
            event.setCancelled(true);
            event.getRightClicked().setPassenger(player);
        }
    }
}
