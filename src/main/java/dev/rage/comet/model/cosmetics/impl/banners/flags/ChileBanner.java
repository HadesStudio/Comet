package dev.rage.comet.model.cosmetics.impl.banners.flags;

import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class ChileBanner extends Banner {

    @Override
    public ItemStack getItem() {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setBaseColor(DyeColor.WHITE);

        bannerMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_MIDDLE));
        bannerMeta.addPattern(new Pattern(DyeColor.BLUE, PatternType.GRADIENT_UP));
        bannerMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));

        banner.setItemMeta(bannerMeta);

        return banner;
    }
}
