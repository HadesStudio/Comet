package dev.rage.comet.model.cosmetics.impl.banners.flags;

import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class MexicoBanner extends Banner {

    @Override
    public ItemStack getItem() {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setBaseColor(DyeColor.WHITE);

        bannerMeta.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_MIDDLE));
        bannerMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
        bannerMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_MIDDLE));
        bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));

        banner.setItemMeta(bannerMeta);

        return banner;
    }
}
