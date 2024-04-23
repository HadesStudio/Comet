package dev.astro.net.model.cosmetics.impl.particles.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.particles.Particle;
import dev.astro.net.model.cosmetics.impl.particles.ParticlesManager;
import fr.mrmicky.fastparticles.ParticleData;
import fr.mrmicky.fastparticles.ParticleType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Test extends Particle {
    private final double radius;
    private final int numPoints;
    private final List<ParticleData.DustOptions> colors;

    private final Map<UUID, Integer> playerVectors;
    private final Map<UUID, Integer> playerColors;

    public Test(Comet plugin, ParticlesManager particlesManager) {
        super(particlesManager);

        radius = 0.6;
        numPoints = 28;

        playerVectors = Maps.newHashMap();
        playerColors = Maps.newHashMap();

        colors = Lists.newArrayList();
        Arrays.asList(new int[]{255, 0, 0}, new int[]{255, 127, 0}, new int[]{255, 255, 0},
                        new int[]{127, 255, 0}, new int[]{0, 255, 0}, new int[]{0, 255, 127},
                        new int[]{0, 255, 255}, new int[]{0, 127, 255}, new int[]{0, 0, 255},
                        new int[]{127, 0, 255}, new int[]{255, 0, 255}, new int[]{255, 0, 127},
                        new int[]{255, 0, 63}, new int[]{255, 63, 0}, new int[]{255, 127, 63},
                        new int[]{255, 191, 63}, new int[]{255, 255, 63}, new int[]{191, 255, 63},
                        new int[]{127, 255, 63}, new int[]{63, 255, 63}, new int[]{63, 255, 127},
                        new int[]{63, 255, 191}, new int[]{63, 255, 255}, new int[]{63, 191, 255},
                        new int[]{63, 127, 255}, new int[]{63, 63, 255}, new int[]{127, 63, 255},
                        new int[]{191, 63, 255}, new int[]{255, 63, 255}, new int[]{255, 63, 191},
                        new int[]{255, 63, 127}, new int[]{255, 95, 95}, new int[]{255, 127, 127},
                        new int[]{255, 159, 159}, new int[]{255, 191, 191})
                .forEach(ints -> colors.add(ParticleData.createDustOptions(Color.fromBGR(ints[0], ints[1], ints[2]), 1)));
    }

    @Override
    public long getDelay() {
        return 2;
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public ParticleType getParticle() {
        return ParticleType.of("REDSTONE");
    }

    @Override
    public void tick(Player player) {
        Location playerLocation = player.getLocation();

        World world = playerLocation.getWorld();
        double centerX = playerLocation.getX();
        double centerY = playerLocation.getY();
        double centerZ = playerLocation.getZ();

        int ticks = playerVectors.getOrDefault(player.getUniqueId(), 0);

        if (ticks >= numPoints) {
            ticks = 0;
        }

        int colorIndex = playerColors.getOrDefault(player.getUniqueId(), 0);

        if (colorIndex >= colors.size()) {
            colorIndex = 0;
        }

        ParticleData.DustOptions color = colors.get(colorIndex);

        double angle = 2 * Math.PI * ticks / numPoints;
        double x = centerX + radius * Math.cos(angle);
        double z = centerZ + radius * Math.sin(angle);
        double y = centerY + 2.5; // Altura de la circunferencia

        Location pointLocation = new Location(world, x, y, z);
        getParticle().spawn(world, pointLocation, 50, color);

        playerColors.put(player.getUniqueId(), colorIndex + 1);
        playerVectors.put(player.getUniqueId(), ticks + 1);
    }
}
