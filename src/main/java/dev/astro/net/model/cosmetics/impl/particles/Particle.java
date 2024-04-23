package dev.astro.net.model.cosmetics.impl.particles;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import fr.mrmicky.fastparticles.ParticleType;
import org.bukkit.entity.Player;

import java.util.Map;

public abstract class Particle {

    public final ParticlesManager particlesManager;

    public Particle(ParticlesManager particlesManager) {
        this.particlesManager = particlesManager;

        CometPlugin.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(CometPlugin.getPlugin(), () -> {
            for (Map.Entry<Player, Particle> playerParticleEntry : particlesManager.getPlayerParticles().entrySet()) {
                if (!playerParticleEntry.getValue().equals(this))
                    continue;

                if (playerParticleEntry.getKey() == null || !playerParticleEntry.getKey().isOnline()) {
                    particlesManager.getPlayerParticles().remove(playerParticleEntry.getKey());
                    continue;
                }

                tick(playerParticleEntry.getKey());
            }
        }, 0L, getDelay());
    }

    public abstract long getDelay();

    public abstract String getName();

    public abstract ParticleType getParticle();

    public abstract void tick(Player player);
}
