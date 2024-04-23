package dev.astro.net.model.cosmetics.impl.mascots;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.mascots.impl.Body;
import dev.astro.net.model.cosmetics.impl.mascots.impl.Heads;
import dev.astro.net.model.cosmetics.impl.mascots.impl.Mascot;
import dev.astro.net.model.cosmetics.impl.mascots.listener.MascotListener;
import dev.astro.net.model.cosmetics.impl.mascots.task.MascotRunnable;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.JavaUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import fr.mrmicky.fastparticles.ParticleType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class MascotManager {

    private final Comet plugin;

    private final Map<UUID, Mascot> mascots;
    private final MascotRunnable mascotRunnable;

    private final List<Body> setBodies;

    public MascotManager(Comet plugin) {
        this.plugin = plugin;
        mascots = Maps.newHashMap();
        setBodies = Lists.newArrayList();

        loadOrRefresh(plugin);

        CometPlugin.getPlugin().getServer().getPluginManager().registerEvents(new MascotListener(CometPlugin.get(), this), CometPlugin.getPlugin());
        CometPlugin.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(CometPlugin.getPlugin(), mascotRunnable = new MascotRunnable(this),
                1L, 1L);
    }

    public void loadOrRefresh(Comet plugin) {
        setBodies.clear();

        FileConfiguration config = plugin.getMascotsFile().getConfiguration();

        for (String key : config.getConfigurationSection("mascots").getKeys(false)) {
            String path = "mascots." + key + ".";
            String title = config.getString(path + "title");

            ParticleType particleType = null;
            if (config.getString(path + "particle") != null && !config.getString(path + "particle").isEmpty()) {
                try {
                    particleType = ParticleType.of(config.getString(path + "particle"));
                } catch (Exception e) {
                    ChatUtil.logger("&cFailed to load particle for " + key);
                }
            }

            Heads head;
            try {
                head = new Heads(config.getString(path + "head-texture"));
            } catch (Exception e) {
                ChatUtil.logger("&cFailed to load head for " + key);
                continue;
            }

            List<ItemStack> armors = Lists.newArrayList();

            ItemStack chestplate = getItemFromConfig(config, key, "chestplate");
            if (chestplate != null)
                armors.add(chestplate);

            ItemStack leggings = getItemFromConfig(config, key, "leggings");
            if (leggings != null)
                armors.add(leggings);

            ItemStack boots = getItemFromConfig(config, key, "boots");
            if (boots != null)
                armors.add(boots);

            List<String> lore = config.getStringList(path + "lore");

            Body body = new Body(head, key, title, armors.toArray(new ItemStack[0]), lore);

            if (particleType != null) {
                body.setParticle(true);
                body.setParticleType(particleType);
            }

            this.setBodies.add(body);
        }
    }

    public void onDisable() {
        mascots.values().forEach(Mascot::onDisable);
    }

    public Body getDefaultBody() {
        return new Body(
                new Heads(
                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1ZWY5MjMwMDMxNzY5YTJlODE5MmFiNDZhMTcxNDQxMGQ1NmMzYjliMzhhMDMwMmEwNThlNDc5NzNkN2M0ZCJ9fX0="),
                "default",
                "&cDefault Head",
                new ItemStack[] {new ItemStack(Material.DIAMOND_CHESTPLATE)},
                Arrays.asList("default", "default2"));
    }

    public boolean exists(String name) {
        return getSetBodies().stream().anyMatch(body -> body.getName().equalsIgnoreCase(name));
    }

    public Body getBody(String name) {
        return getSetBodies().stream().filter(body -> body.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Nullable
    private ItemStack getItemFromConfig(FileConfiguration config, String key, String type) {
        String path = "mascots." + key + ".";
        ItemStack leggings = null;
        if (config.contains(path + type + "-item")) {
            try {
                ItemBuilder itemBuilder = new ItemBuilder(com.cryptomorin.xseries.XMaterial.matchXMaterial(config.getString(path + type + "-item.material")).get().parseMaterial());
                if (itemBuilder.getItemStack().getType().name().contains("LEATHER")) {
                    int[] rgb = JavaUtil.getRGB(config.getString(path  + type+ "-item.color"));
                    itemBuilder.setRGBColor(rgb[0], rgb[1], rgb[2]);
                }
                itemBuilder.setEnchanted(config.getBoolean(path + type + "-item.enchanted"));

                leggings = itemBuilder.build();
            } catch (Exception e) {
                ChatUtil.logger("&cFailed to load " + type + " for " + key);
                return null;
            }
        }

        return leggings;
    }
}
