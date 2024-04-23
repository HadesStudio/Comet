package dev.astro.net.utilities.menu.buttons;

import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.pagination.PaginatedMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PageInfoButton extends Button {

    private final PaginatedMenu paginatedMenu;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.NETHER_STAR.parseMaterial())
                .setName("&ePage Info")
                .setLore("&e" + paginatedMenu.getPage() + "&7/&a" + paginatedMenu.getPages(player))
                .build();
    }

    @Override
    public boolean shouldCancel(Player player, int slot, ClickType clickType) {
        return true;
    }
}
