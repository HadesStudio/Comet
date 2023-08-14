package dev.rage.comet.model.cosmetics.impl.outfits.entries.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.Outfit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomOutfit extends Outfit {

    private final Comet plugin;

    private final String name, displayName;
    private final List<String> description;
    private final ItemStack[] armor;

    @Override
    public String getPermission() {
        return "comet.cosmetic.outfit." + getName().toLowerCase().replace(" ", "_");
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }
}
