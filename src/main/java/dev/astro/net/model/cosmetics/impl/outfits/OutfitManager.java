package dev.astro.net.model.cosmetics.impl.outfits;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.outfits.entries.Outfit;
import dev.astro.net.model.cosmetics.impl.outfits.entries.impl.CustomOutfit;
import dev.astro.net.model.cosmetics.impl.outfits.entries.impl.LeatherOutfit;
import dev.astro.net.utilities.JavaUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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

    public void loadOrRefresh(Comet plugin) {
         leatherOutfits.clear();
         customOutfits.clear();

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

            String materialType = config.getString(path + type + ".type");

            if (materialType.equals("PLAYER_HEAD")) {
                ItemStack head = new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem())
                        .build();

                ItemMeta itemMeta = head.getItemMeta();
                if (itemMeta instanceof SkullMeta) {
                    SkullMeta skullMeta = (SkullMeta) itemMeta;
                    SkullUtils.applySkin(skullMeta, config.getString(path + type + ".texture"));
                    head.setItemMeta(skullMeta);
                }
                armor[i] = head;
            } else {
                Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(materialType);
                if (!xMaterial.isPresent()) {
                    throw new IllegalArgumentException("Invalid material type for " + type + " in " + path + type + ".type");
                }
                ItemStack itemStack = xMaterial.get().parseItem();

                if (itemStack != null) {
                    if (itemStack.getType().name().contains("LEATHER")) {
                        int[] rgb = JavaUtil.getRGB(config.getString(path + type + ".rgb"));
                        armor[i] = new ItemBuilder(itemStack)
                                .setRGBColor(rgb[0], rgb[1], rgb[2])
                                .build();
                    } else {
                        armor[i] = new ItemStack(itemStack);
                    }
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
