package dev.rage.comet.tablist.skin.custom;

import dev.rage.comet.tablist.util.Skin;
import lombok.Getter;

@Getter
public class CustomSkin extends Skin {

    private final String name;

    public CustomSkin(String name, String value, String signature) {
        super(value, signature);
        this.name = name;
    }
}
