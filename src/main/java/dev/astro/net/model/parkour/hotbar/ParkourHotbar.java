package dev.astro.net.model.parkour.hotbar;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public abstract class ParkourHotbar {

    private final ItemStack item;
    private final int itemSlot;

    protected ParkourHotbar(ItemStack item, int itemSlot) {
        this.item = item;
        this.itemSlot = itemSlot;
    }

    public abstract void onItemInteract(Player player);
}
