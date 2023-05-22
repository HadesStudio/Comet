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

public class MexicoBanner extends Banner {

    @Override
    public String getId() {
        return "mexico";
    }

    @Override
    public String getName() {
        return "&aMex&cico &eFlag";
    }

    @Override
    public ItemStack getItem() {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setDisplayName(ChatUtil.translate(getName()));
        bannerMeta.setBaseColor(DyeColor.WHITE);

        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 12), PatternType.getByIdentifier("flo")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 0), PatternType.getByIdentifier("ls")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 0), PatternType.getByIdentifier("rs")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 0), PatternType.getByIdentifier("bt")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 0), PatternType.getByIdentifier("tt")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 13), PatternType.getByIdentifier("bs")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 14), PatternType.getByIdentifier("ts")));

        bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        banner.setItemMeta(bannerMeta);
        return banner;
    }
}
