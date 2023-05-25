package dev.rage.comet.model.cosmetics.impl.gadgets;

import dev.rage.comet.Comet;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class Gadget implements Listener {

    public final Comet plugin;

    public Gadget(Comet plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        System.out.println("Gadget: " + getName() + " has been registered!");
    }

    public abstract String getName();

    public abstract ItemStack getIcon();

    public abstract void onUsage(Player player);

    public boolean isItem(@Nullable ItemStack hand, @Nullable ItemStack itemStack) {
        if (hand == null || itemStack == null) return false;
        return hand.isSimilar(itemStack);
    }

    public ItemStack getItem() {
        return getIcon();
    }

    /*
        All permissions will follow this format: comet.gadget.<gadget-name>.
        will follow this format: comet.gadget.<gadget-name>, if the name has capital letters, it will automatically change to lowercase,
        and if it has a space, it will be replaced by a "-".
    */
    public String getPermission() {
        return "comet.gadget." + getName().replace(" ", "-").toLowerCase();
    }
}
