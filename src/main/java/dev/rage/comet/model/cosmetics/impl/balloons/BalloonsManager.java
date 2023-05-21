package dev.rage.comet.model.cosmetics.impl.balloons;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.balloons.tasks.BalloonRunnable;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class BalloonsManager {

    private final Map<UUID, Balloon> balloons;
    private final List<Heads> heads;

    public BalloonsManager(Comet plugin) {
        this.balloons = Maps.newHashMap();
        this.heads = Lists.newArrayList();

        init(plugin);

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BalloonRunnable(this), 0L, 1L);
    }

    private void init(Comet plugin) {
        for (String key : plugin.getBalloonsFile().getConfiguration().getConfigurationSection("heads").getKeys(false)) {
            try {
                String path = "heads." + key + ".";
                heads.add(new Heads(key,
                        plugin.getBalloonsFile().getConfiguration().getString(path + "displayName"),
                        plugin.getBalloonsFile().getConfiguration().getString(path + "texture")));
            } catch (Exception e) {
                throw new RuntimeException("Failed to load head " + key);
            }
        }
    }

    public Heads getDefaultHead() {
        return new Heads("Default",
                "&7Default",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2M5MWVjMDhjNzQ2MTQ1MzgxNzg0OWJmYjgzMTJhY2E5NDU2NmU1ZjZjMTlmODQ5NGVjMTk0NWJlZTBjYjRjZiJ9fX0=");
    }
}
