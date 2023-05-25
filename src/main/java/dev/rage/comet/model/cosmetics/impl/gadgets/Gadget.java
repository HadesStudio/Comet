package dev.rage.comet.model.cosmetics.impl.gadgets;

import dev.rage.comet.Comet;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class Gadget implements Listener {

    public final Comet plugin;

    public Gadget(Comet plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public abstract String getName();

    public abstract ItemStack getIcon();

    public boolean isItem(@Nullable ItemStack hand, @Nullable ItemStack itemStack) {
        if (hand == null || itemStack == null) return false;
        return hand.isSimilar(itemStack);
    }
}
