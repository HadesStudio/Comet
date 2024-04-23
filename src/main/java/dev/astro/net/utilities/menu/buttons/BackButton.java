package dev.astro.net.utilities.menu.buttons;

import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BackButton extends Button {

    private final Menu back;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.REDSTONE.parseMaterial())
                .setName("&cBack")
                .build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        playNeutral(player);
        this.back.openMenu(player);
    }
}
