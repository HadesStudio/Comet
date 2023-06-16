package dev.rage.comet.model.cosmetics.impl.particles;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.particles.impl.Test;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Map;

@Getter
public class ParticlesManager {

    private final LinkedList<Particle> particles;
    private final Map<Player, Particle> playerParticles;

    public ParticlesManager(Comet plugin) {
        this.particles = Lists.newLinkedList();
        this.playerParticles = Maps.newHashMap();

        loadOrRefresh(plugin);
    }

    private void loadOrRefresh(Comet plugin) {
        particles.add(new Test(plugin, this));
    }
}
