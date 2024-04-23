package dev.astro.net.model.cosmetics.menu.buttons;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.balloons.BalloonManager;
import dev.astro.net.model.cosmetics.impl.balloons.menu.BalloonMenu;
import dev.astro.net.user.UserManager;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class BalloonButton extends Button {

    private final Comet plugin;
    private final BalloonManager balloonsManager;
    private final UserManager userManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3)
                .setName(plugin.getCosmeticsFile().getString("menu.balloon_title"))
                .setLore(plugin.getCosmeticsFile().getStringList("menu.balloon_lore"))
                .setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ3ZjVlMmU1MjM1YjY3OWY4ZGI5YzQyZTg1OWM4OGFhNzgyN2IxZmI1MTgyYzA3NzAzYzQ1NmU5MTI1Y2Y1ZiJ9fX0=")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        BalloonMenu balloonMenu = new BalloonMenu(plugin, balloonsManager, userManager);
        balloonMenu.openMenu(player);
    }
}
