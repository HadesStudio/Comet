package dev.rage.comet.utilities.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CloseButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.REDSTONE.parseMaterial())
                .setName("&cClose")
                .build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        playNeutral(player);
        player.closeInventory();
    }
}
