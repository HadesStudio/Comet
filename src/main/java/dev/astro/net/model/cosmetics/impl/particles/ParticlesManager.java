package dev.astro.net.model.cosmetics.impl.particles;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.particles.impl.Test;
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

    public boolean exists(String name) {
        return particles.stream().anyMatch(particle -> particle.getName().equalsIgnoreCase(name));
    }

    public void apply(Player player, Particle particle) {
        playerParticles.put(player, particle);
    }

    public Particle getParticle(String name) {
        return particles.stream().filter(particle -> particle.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
