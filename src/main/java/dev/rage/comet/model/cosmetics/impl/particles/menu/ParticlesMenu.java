package dev.rage.comet.model.cosmetics.impl.particles.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.model.cosmetics.impl.particles.Particle;
import dev.rage.comet.model.cosmetics.impl.particles.ParticlesManager;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class ParticlesMenu extends Menu {

    private final ParticlesManager particlesManager;

    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Particles";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (Particle particle : particlesManager.getParticles()) {
            buttons.put(buttons.size(), Button.fromItem(new ItemBuilder(Material.SLIME_BALL)
                        .setName("&a" + particle.getName())
                        .build(), player1 -> {
                    particlesManager.getPlayerParticles().put(player1, particle);
            }));
        }

        return buttons;
    }
}
