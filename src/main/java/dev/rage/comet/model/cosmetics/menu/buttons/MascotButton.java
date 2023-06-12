package dev.rage.comet.model.cosmetics.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.mascots.MascotManager;
import dev.rage.comet.model.cosmetics.impl.mascots.menu.SelectMascotMenu;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class MascotButton extends Button {

    private final Comet plugin;
    private final MascotManager mascotManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.ARMOR_STAND.parseItem())
                .setName("&6&lMascots")
                .setLore("",
                        "&7Select a mascot to use")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new SelectMascotMenu(plugin, mascotManager).openMenu(player);
    }
}
