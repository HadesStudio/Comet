package dev.rage.comet.model.cosmetics.impl.banners;

import com.google.common.collect.Lists;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.banners.flags.*;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class BannersManager {

    private final List<Banner> banners;

    public BannersManager(Comet plugin) {
        this.banners = Lists.newArrayList();

        banners.addAll(Arrays.asList(
                new ChileBanner(),
                new ArgentinaBanner(),
                new ColombiaBanner(),
                new MexicoBanner(),
                new SpainBanner(),
                new USABanner()));
    }
}
