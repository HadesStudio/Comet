package dev.rage.comet.model.cosmetics.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonsManager;
import dev.rage.comet.model.cosmetics.impl.balloons.menu.BalloonsMenu;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class BalloonsButton extends Button {

    private final BalloonsManager balloonsManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3)
                .setName("&6&lBalloons")
                .setLore("",
                        "&7Click to view all balloons.")
                .setSkullOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ3ZjVlMmU1MjM1YjY3OWY4ZGI5YzQyZTg1OWM4OGFhNzgyN2IxZmI1MTgyYzA3NzAzYzQ1NmU5MTI1Y2Y1ZiJ9fX0=")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new BalloonsMenu(balloonsManager).openMenu(player);
    }
}
