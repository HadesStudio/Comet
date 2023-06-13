package dev.rage.comet.model.cosmetics.impl.particles.impl;

import dev.rage.comet.model.cosmetics.impl.particles.Particle;
import dev.rage.comet.model.cosmetics.impl.particles.ParticlesManager;
import dev.rage.comet.utilities.compatible.CompatibleParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
            Location test = player.getLocation();
            Vector direction = test.getDirection();

            // get upper head
            Location playerLocation = test.clone().add(direction.clone()).add(0, 1.5, 0);

            double radius = 0.5; // Radio del diamante
            double height = 0.8; // Altura del diamante

            int centerX = playerLocation.getBlockX();
            int centerY = playerLocation.getBlockY();
            int centerZ = playerLocation.getBlockZ();

            for (double y = centerY; y < centerY + height; y+=0.2) {
                double currentHeight = y - centerY;
                double radiusOffset = radius - currentHeight;

                for (double x = centerX - radiusOffset; x <= centerX + radiusOffset; x+=0.2) {
                    for (double z = centerZ - radiusOffset; z <= centerZ + radiusOffset; z+=0.2) {
                        // Crea una partícula en las coordenadas (x, y, z) con el tipo de partícula deseado
                        Location newLoc = new Location(playerLocation.getWorld(), x, y, z);
                        getParticle().display(0, 0, 0, 0, 1, newLoc, 50);
                    }
                }
            }

        });
    }
}
