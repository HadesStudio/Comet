package dev.rage.comet.model.cosmetics.impl.banners;

import dev.rage.comet.model.cosmetics.impl.banners.flags.*;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class BannerManager {

    private final Map<String, Banner> banners;

    public BannerManager() {
        this.banners = new LinkedHashMap<>();
        this.banners.put("chile", new ChileBanner());
        this.banners.put("argentina", new ArgentinaBanner());
        this.banners.put("colombia", new ColombiaBanner());
        this.banners.put("mexico", new MexicoBanner());
        this.banners.put("spain", new SpainBanner());
        this.banners.put("usa", new USABanner());
    }

    public boolean exists(String name) {
        return banners.containsKey(name);
    }

    public Banner getBanner(String name) {
        return banners.getOrDefault(name, null);
    }
}
