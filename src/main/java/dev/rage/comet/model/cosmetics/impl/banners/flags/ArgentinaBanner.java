package dev.rage.comet.model.cosmetics.impl.banners.flags;

import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class ArgentinaBanner extends Banner {

    @Override
    public ItemStack getItem() {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setBaseColor(DyeColor.WHITE);

        bannerMeta.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.STRIPE_BOTTOM));
        bannerMeta.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.STRIPE_TOP));
        bannerMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
        bannerMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.CREEPER));

        banner.setItemMeta(bannerMeta);

        return banner;
    }
}
