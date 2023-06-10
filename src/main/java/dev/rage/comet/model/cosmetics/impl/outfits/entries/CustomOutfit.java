package dev.rage.comet.model.cosmetics.impl.outfits.entries;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@AllArgsConstructor
public abstract class CustomOutfit extends Outfit {

    private final String name;
    private ItemStack customHead;
    private String chestPlateColor, leggingsColor, bootsColor;

    @Override
    public String getPermission() {
        return "comet.outfit.custom." + getName().toLowerCase().replace(" ", "_");
    }

    @Override
    public ItemStack[] getArmor() {
        ItemStack skull = customHead;
        ItemStack[] armor = getLeatherArmor();

        for (ItemStack item : armor) {
            if (item != null) {
                if (item.getItemMeta() instanceof LeatherArmorMeta) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    Color color = null;

                    if (item.getType().name().contains("CHESTPLATE")) {
                        color = Color.fromRGB(getRGB(chestPlateColor)[0], getRGB(chestPlateColor)[1], getRGB(chestPlateColor)[2]);
                    } else if (item.getType().name().contains("LEGGINGS")) {
                        color = Color.fromRGB(getRGB(leggingsColor)[0], getRGB(leggingsColor)[1], getRGB(leggingsColor)[2]);
                    } else if (item.getType().name().contains("BOOTS")) {
                        color = Color.fromRGB(getRGB(bootsColor)[0], getRGB(bootsColor)[1], getRGB(bootsColor)[2]);
                    }

                    meta.setColor(color);
                    item.setItemMeta(meta);
                }
                else if (item.getType().name().contains("SKULL")) {
                    item = customHead;
                }
            }
        }
        return super.getArmor();
    }

    private int[] getRGB(String color) {
        int[] rgb = new int[3];
        String[] split = color.split(",");
        for (int i = 0; i < split.length; i++) {
            rgb[i] = Integer.parseInt(split[i]);
        }
        return rgb;
    }
}
