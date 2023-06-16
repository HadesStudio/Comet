package dev.rage.comet.model.cosmetics.impl.particles;

import dev.rage.comet.Comet;
import dev.rage.comet.utilities.compatible.CompatibleParticle;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class Particle {

    public final ParticlesManager particlesManager;

    public Particle(Comet plugin, ParticlesManager particlesManager) {
        this.particlesManager = particlesManager;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Map.Entry<Player, Particle> playerParticleEntry : particlesManager.getPlayerParticles().entrySet()) {
                if (!playerParticleEntry.getValue().equals(this))
                    continue;

                if (playerParticleEntry.getKey() == null || !playerParticleEntry.getKey().isOnline()) {
                    particlesManager.getPlayerParticles().remove(playerParticleEntry.getKey());
                    continue;
                }

                tick(playerParticleEntry.getKey()).join();
            }
        }, 0L, getDelay());
    }

    public abstract long getDelay();

    public abstract String getName();

    public abstract CompatibleParticle getParticle();

    public abstract CompletableFuture<Void> tick(Player player);
}
