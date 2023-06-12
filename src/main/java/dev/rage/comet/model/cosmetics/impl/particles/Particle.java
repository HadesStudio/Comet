package dev.rage.comet.model.cosmetics.impl.particles;

import dev.rage.comet.utilities.compatible.CompatibleParticle;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public abstract class Particle {

    public final ParticlesManager particlesManager;

    public Particle(ParticlesManager particlesManager) {
        this.particlesManager = particlesManager;
    }

    public abstract String getName();

    public abstract CompatibleParticle getParticle();

    public abstract CompletableFuture<Void> tick(Player player);
}
