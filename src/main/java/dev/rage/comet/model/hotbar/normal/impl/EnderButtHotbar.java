package dev.rage.comet.model.hotbar.normal.impl;

import dev.rage.comet.utilities.PlayerUtil;
import dev.rage.comet.model.hotbar.normal.NormalHotbar;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class EnderButtHotbar extends NormalHotbar {

    public EnderButtHotbar(String name) {
        super(name);
    }

    @Override
    public void onItemInteract(Player player) {
        player.setVelocity(player.getLocation().getDirection().normalize().setY(2.0));
        player.setVelocity(player.getLocation().getDirection().normalize().multiply(2F));
        PlayerUtil.playSound(player, "ORB_PICKUP");
    }
}
