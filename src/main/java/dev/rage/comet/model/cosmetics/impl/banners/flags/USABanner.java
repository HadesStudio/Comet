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

public class USABanner extends Banner {

    @Override
    public String getName() {
        return "&2Unit&fedSt&cates &eFlag";
    }

    @Override
    public ItemStack getItem() {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setDisplayName(ChatUtil.translate(getName()));
        bannerMeta.setBaseColor(DyeColor.WHITE);

        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 11), PatternType.getByIdentifier("bri")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 11), PatternType.getByIdentifier("ss")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 14), PatternType.getByIdentifier("ms")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 14), PatternType.getByIdentifier("hhb")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 14), PatternType.getByIdentifier("vh")));
        bannerMeta.addPattern(new Pattern(DyeColor.getByData((byte) 0), PatternType.getByIdentifier("ss")));

        bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        banner.setItemMeta(bannerMeta);

        return banner;
    }
}
