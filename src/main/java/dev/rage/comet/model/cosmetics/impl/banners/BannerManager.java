package dev.rage.comet.model.cosmetics.impl.banners;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.utilities.ChatUtil;
import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class BannerManager {

    private final Map<String, Banner> banners;

    public BannerManager(Comet plugin) {
        this.banners = new LinkedHashMap<>();

        this.loadOrCreate(plugin);
    }

    private void loadOrCreate(Comet plugin) {
        FileConfiguration config = plugin.getBannersFile().getConfiguration();
        for (String key : config.getConfigurationSection("banners").getKeys(false)) {
            String path = "banners." + key + ".";

            this.banners.put(key, new Banner() {
                @Override
                public String getId() {
                    return key;
                }

                @Override
                public String getName() {
                    return config.getString(path + "display-name");
                }

                @Override
                public ItemStack getItem() {
                    ItemStack banner = new ItemStack(XMaterial.BLACK_BANNER.parseMaterial());
                    BannerMeta meta = (BannerMeta) banner.getItemMeta();

                    meta.setDisplayName(ChatUtil.translate(getName()));
                    meta.setBaseColor(DyeColor.valueOf(config.getString(path + "base-color")));

                    config.getStringList(path + "patterns").forEach(pattern -> {
                        meta.addPattern(new Pattern(DyeColor.getByData(getByteFromString(pattern)), PatternType.getByIdentifier(getIdFromString(pattern))));
                    });

                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

                    banner.setItemMeta(meta);
                    return banner;
                }
            });
        }
    }

    public boolean exists(String name) {
        return banners.containsKey(name);
    }

    public Banner getBanner(String name) {
        return banners.getOrDefault(name, null);
    }

    private byte getByteFromString(String string) {
        return Byte.parseByte(string.split(":")[0]);
    }

    private String getIdFromString(String string) {
        return string.split(":")[1];
    }
}
