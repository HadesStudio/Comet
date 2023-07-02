package dev.rage.comet.model.cosmetics.impl.outfits;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.Outfit;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.impl.CustomOutfit;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.impl.LeatherOutfit;
import dev.rage.comet.utilities.JavaUtil;
import dev.rage.comet.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Getter
public class OutfitManager {

    private final LinkedList<LeatherOutfit> leatherOutfits;
    private final LinkedList<CustomOutfit> customOutfits;

    public OutfitManager(Comet plugin) {
        leatherOutfits = Lists.newLinkedList();
        customOutfits = Lists.newLinkedList();

        loadOrRefresh(plugin);
    }

    private void loadOrRefresh(Comet plugin) {
        // Load the leather outfits
        for (Table.Cell<Object, Object, Object> objectObjectObjectCell : ImmutableTable.builder()
                .put("White", ChatColor.WHITE, Color.WHITE) // Here you can add more colors
                .put("Orange", ChatColor.GOLD, Color.ORANGE)
                .build()
                .cellSet()) {

            leatherOutfits.add(new LeatherOutfit(
                    (String) objectObjectObjectCell.getRowKey(),
                    (ChatColor) objectObjectObjectCell.getColumnKey(),
                    (Color) objectObjectObjectCell.getValue()));
        }

        loadCustomOutfits(plugin);
    }

    private void loadCustomOutfits(Comet plugin) {
        FileConfiguration config = plugin.getCustomOutfitsFile().getConfiguration();

        for (String outfits : config.getConfigurationSection("outfits").getKeys(false)) {
            String path = "outfits." + outfits + ".";
            String displayName = config.getString(path + "displayName");
            List<String> description = config.getStringList(path + "description");

            customOutfits.add(new CustomOutfit(plugin, outfits, displayName, description, loadArmor(path, config)));
        }
    }

    private ItemStack[] loadArmor(String path, FileConfiguration config) {
        ItemStack[] armor = new ItemStack[4];
        List<String> types = Arrays.asList("boots", "leggings", "chestplate", "helmet");

        for (int i = 0; i < 4; i++) {
            String type = types.get(i);

            Optional<com.cryptomorin.xseries.XMaterial> xMaterial = com.cryptomorin.xseries.XMaterial.matchXMaterial(config.getString(path + type + ".type"));
            if (!xMaterial.isPresent()) {
                throw new IllegalArgumentException("Invalid material type for " + type + " in " + path + type + ".type");
            }

            ItemStack itemStack = xMaterial.get().parseItem();

            if (itemStack != null) {
                if (type.equals("helmet") && itemStack.getType().name().contains("SKULL")) {
                    armor[i] = new ItemBuilder(com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.parseItem())
                            .setSkullOwner(config.getString(path + type + ".texture"))
                            .build();
                }
                else if (itemStack.getType().name().contains("LEATHER")) {
                    int[] rgb = JavaUtil.getRGB(config.getString(path + type + ".rgb"));
                    armor[i] = new ItemBuilder(itemStack)
                            .setRGBColor(rgb[0], rgb[1], rgb[2])
                            .build();
                }
                else {
                    armor[i] = new ItemStack(itemStack);
                }
            }
        }

        return armor;
    }

    public boolean exists(String name) {
        return leatherOutfits.stream().anyMatch(outfit -> outfit.getName().equalsIgnoreCase(name))
                || customOutfits.stream().anyMatch(outfit -> outfit.getName().equalsIgnoreCase(name));
    }

    public Outfit getOutfit(String name) {
        return leatherOutfits.stream().filter(outfit -> outfit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
