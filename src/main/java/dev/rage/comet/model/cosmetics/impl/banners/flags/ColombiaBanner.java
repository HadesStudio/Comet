package dev.rage.comet.model.cosmetics.impl.banners.flags;

import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import dev.rage.comet.utilities.ChatUtil;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class ColombiaBanner extends Banner {

    @Override
    public String getId() {
        return "colombia";
    }

    @Override
    public String getName() {
        return "&eCol&2omb&cia &eBanner";
    }

    @Override
    public ItemStack getItem() {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setDisplayName(ChatUtil.translate(getName()));
        bannerMeta.setBaseColor(DyeColor.BLUE);

        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 4), PatternType.getByIdentifier("ts")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 14), PatternType.getByIdentifier("bs")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 4), PatternType.getByIdentifier("ts")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 14), PatternType.getByIdentifier("bs")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 14), PatternType.getByIdentifier("bs")));

        bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        banner.setItemMeta(bannerMeta);
        return banner;
    }
}
