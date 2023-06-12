package dev.rage.comet.model.cosmetics.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.outfits.OutfitManager;
import dev.rage.comet.model.cosmetics.impl.outfits.menus.OutfitMenu;
import dev.rage.comet.utilities.item.ItemBuilder;
import dev.rage.comet.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class OutfitButton extends Button {

    private final Comet plugin;
    private final OutfitManager outfitManager;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.GOLDEN_CHESTPLATE.parseItem())
                .setName("&6&lOutfit")
                .setLore("",
                        "&7Select a outfit to use")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new OutfitMenu(outfitManager).openMenu(player);
    }
}
