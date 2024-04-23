package dev.astro.net.tablist.skin.custom;

import dev.astro.net.tablist.util.Skin;
import lombok.Getter;

@Getter
public class CustomSkin extends Skin {

    private final String name;

    public CustomSkin(String name, String value, String signature) {
        super(value, signature);
        this.name = name;
    }
}
