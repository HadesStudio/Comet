package dev.rage.comet.model.cosmetics.impl.mascots;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.mascots.impl.Body;
import dev.rage.comet.model.cosmetics.impl.mascots.impl.Heads;
import dev.rage.comet.model.cosmetics.impl.mascots.impl.Mascot;
import dev.rage.comet.model.cosmetics.impl.mascots.listener.MascotListener;
import dev.rage.comet.model.cosmetics.impl.mascots.task.MascotRunnable;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.compatible.CompatibleParticle;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class MascotManager {

    private final Comet plugin;

    private final Map<UUID, Mascot> mascots;
    private final MascotRunnable mascotRunnable;

    private final List<Body> setBodies;

    // Possible future method to customize your mascots
    /*private final Map<String , ItemStack> bodies;
    private final List<Heads> heads;
    private final List<Particle> particles;*/

    public MascotManager(Comet plugin) {
        this.plugin = plugin;
        mascots = Maps.newHashMap();
        setBodies = Lists.newArrayList();

        /*bodies = Maps.newHashMap();
        heads = Lists.newArrayList();
        particles = Lists.newArrayList();*/

        loadOrRefresh(plugin);

        plugin.getServer().getPluginManager().registerEvents(new MascotListener(plugin, this), plugin);

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, mascotRunnable = new MascotRunnable(this),
                1L, 1L);
    }

    private void loadOrRefresh(Comet plugin) {
        Body testBody = new Body(
                new Heads("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjAyMzI5Mzg5YWM4NmIzNjVmNGQzZmEyNDJlZmZiYmU1ZGUyYzY3ZWVhZjkxZjVhNjM0ZmVmODU1MmNhMzhlOCJ9fX0="),
                "Batman",
                ChatUtil.translate("&a&lI'm Batman"),
                new ItemStack(XMaterial.DIAMOND_CHESTPLATE.parseMaterial()));

        testBody.setCompatibleParticleEffect(CompatibleParticle.CLOUD);
        testBody.setParticle(true);

        setBodies.add(testBody);

        // Possible method to load the mascots from a config file
        /*FileConfiguration config = plugin.getBalloonConfig().getConfiguration();

        for (String key : config.getKeys(false)) {
            for (String s : config.getConfigurationSection(key).getKeys(false)) {
                String path = key + "." + s + ".";
                try {
                    Heads head = new Heads(config.getString(path + "texture"));

                    ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
                    if (config.contains(path + "chestplate")) {
                        try {
                            chestPlate = ItemStackSerializer.itemFrom64(config.getString(path + "chestplate"));
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to deserialize chestplate for " + key + "." + s);
                        }
                    }

                    Body body = new Body(head, s.replace("_", " "), config.getString(path + "displayName"), chestPlate);

                    if (config.contains(path + "particle")) {
                        body.setParticle(true);

                        try {
                            body.setParticleEffect(Particle.fromName(config.getString(path + "particle")));
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to deserialize particle for " + key + "." + s);
                        }
                    }

                    setBodies.add(body);
                    Logger.deb("Loaded mascot " + key + "." + s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    public Body getDefaultBody() {
        return new Body(
                new Heads(
                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1ZWY5MjMwMDMxNzY5YTJlODE5MmFiNDZhMTcxNDQxMGQ1NmMzYjliMzhhMDMwMmEwNThlNDc5NzNkN2M0ZCJ9fX0="),
                "default",
                "&cDefault Head",
                new ItemStack(Material.DIAMOND_CHESTPLATE));
    }

    public boolean exists(String name) {
        return getSetBodies().stream().anyMatch(body -> body.getName().equalsIgnoreCase(name));
    }

    public Body getBody(String name) {
        return getSetBodies().stream().filter(body -> body.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
