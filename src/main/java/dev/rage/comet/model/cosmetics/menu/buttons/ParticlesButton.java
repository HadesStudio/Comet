package dev.rage.comet.model.cosmetics.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.particles.ParticlesManager;
import dev.rage.comet.model.cosmetics.impl.particles.menu.ParticlesMenu;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class ParticlesButton extends Button {

    private final ParticlesManager particlesManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.SLIME_BALL.parseItem())
                .setName("&6&lParticles")
                .setLore("",
                        "&7Select a particle to use")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new ParticlesMenu(particlesManager).openMenu(player);
    }
}
