package dev.rage.comet.model.cosmetics.menu.buttons;

import dev.rage.comet.utilities.Comet;
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

    private final Comet plugin;
    private final ParticlesManager particlesManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.SLIME_BALL.parseItem())
                .setName(plugin.getCosmeticsFile().getString("menu.particles_title"))
                .setLore(plugin.getCosmeticsFile().getStringList("menu.particles_lore"))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new ParticlesMenu(particlesManager).openMenu(player);
    }
}
