package dev.rage.comet.model.cosmetics.impl.outfits.impl;

import dev.rage.comet.model.cosmetics.impl.outfits.entries.LeatherOutfit;
import org.bukkit.ChatColor;
import org.bukkit.Color;

public class WhiteOutfit extends LeatherOutfit {

    @Override
    public String getName() {
        return "White";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.WHITE;
    }

    @Override
    public Color getArmorColor() {
        return Color.WHITE;
    }
}
