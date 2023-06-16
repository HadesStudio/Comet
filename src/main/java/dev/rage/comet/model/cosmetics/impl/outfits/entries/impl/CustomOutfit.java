package dev.rage.comet.model.cosmetics.impl.outfits.entries.impl;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.Outfit;
import dev.rage.comet.utilities.item.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class CustomOutfit extends Outfit {

    private final Comet plugin;

    private final String name, displayName;
    private final List<String> description;
    private final ItemStack[] armor;

    @Override
    public String getPermission() {
        return "comet.outfit.custom." + getName().toLowerCase().replace(" ", "_");
    }
}
