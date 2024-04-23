package dev.astro.net.model.cosmetics.impl.gadgets;

import com.google.common.collect.Maps;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.JavaUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public abstract class Gadget implements Listener {

    public final Comet plugin;
    public final Map<UUID, Long> cooldowns;

    public Gadget(Comet plugin) {
        this.plugin = plugin;
        this.cooldowns = Maps.newHashMap();

        CometPlugin.getPlugin().getServer().getPluginManager().registerEvents(this, CometPlugin.getPlugin());
    }

    public abstract String getName();

    public abstract ItemStack getIcon();

    public abstract ItemStack getItem();

    public abstract void onUsage(Player player);

    public abstract void onRemoval(Player player);

    public abstract void onDisable();

    public boolean isItem(@Nullable ItemStack hand, @Nullable ItemStack itemStack) {
        if (hand == null || itemStack == null) return false;
        return hand.isSimilar(itemStack);
    }

    /*
        All permissions will follow this format: comet.gadget.<gadget-name>.
        will follow this format: comet.gadget.<gadget-name>, if the name has capital letters, it will automatically change to lowercase,
        and if it has a space, it will be replaced by a "-".
    */
    public String getPermission() {
        return "comet.cosmetic.gadget." + getName().replace(" ", "_").toLowerCase();
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }

    public boolean hasCooldown(UUID uuid) {
        return cooldowns.containsKey(uuid) && cooldowns.get(uuid) > System.currentTimeMillis();
    }

    public void setCooldown(UUID uuid, long time) {
        cooldowns.put(uuid, System.currentTimeMillis() + time);
    }

    public long getCooldown(UUID uuid) {
        return cooldowns.getOrDefault(uuid, 0L);
    }

    public void removeCooldown(UUID uuid) {
        cooldowns.remove(uuid);
    }

    public String getFormattedCooldown(UUID uuid) {
        return JavaUtil.formatMillis(getCooldown(uuid));
    }
}
