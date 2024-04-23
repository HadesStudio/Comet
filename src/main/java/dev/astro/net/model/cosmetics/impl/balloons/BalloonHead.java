package dev.astro.net.model.cosmetics.impl.balloons;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public class BalloonHead {

    private final String id;
    private ItemStack head;

    public BalloonHead(String id) {
        this.id = id;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission("comet.cosmetic.balloon." + id);
    }
}
