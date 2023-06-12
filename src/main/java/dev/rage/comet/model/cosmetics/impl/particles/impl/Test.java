package dev.rage.comet.model.cosmetics.impl.particles.impl;

import dev.rage.comet.model.cosmetics.impl.particles.Particle;
import dev.rage.comet.model.cosmetics.impl.particles.ParticlesManager;
import dev.rage.comet.utilities.compatible.CompatibleParticle;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class Test extends Particle {

    public Test(ParticlesManager particlesManager) {
        super(particlesManager);
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public CompatibleParticle getParticle() {
        return CompatibleParticle.CLOUD;
    }

    @Override
    public CompletableFuture<Void> tick(Player player) {
        return CompletableFuture.runAsync(() -> {
            getParticle().display(0, 0, 0, 1, 1, player.getLocation(), 50);
        });
    }
}
