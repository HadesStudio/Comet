package dev.rage.comet.model.cosmetics.impl.banners.flags;

import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class USABanner extends Banner {

    @Override
    public ItemStack getItem() {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setBaseColor(DyeColor.WHITE);

        for (int i = 0; i < 13; i++) {
            bannerMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
        }

        bannerMeta.addPattern(new Pattern(DyeColor.BLUE, PatternType.GRADIENT_UP));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 11; col++) {
                if ((row + col) % 2 == 0) {
                    bannerMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE)); // ????
                }
            }
        }

        banner.setItemMeta(bannerMeta);

        return banner;
    }
}
