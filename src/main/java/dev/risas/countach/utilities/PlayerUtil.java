package dev.risas.countach.utilities;

import com.cryptomorin.xseries.XSound;
import lombok.experimental.UtilityClass;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Countach
 * Date: 14-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class PlayerUtil {

    public void playSound(Player player, String sound) {
        if (!sound.isEmpty()) {
            player.playSound(player.getLocation(), XSound.matchXSound(sound).get().parseSound(), 1.0F, 1.0F);
        }
    }

    public void playEffect(Player player, String effect) {
        if (!effect.isEmpty()) {
            try {
                player.getWorld().playEffect(player.getLocation(), Effect.valueOf(effect), 1);
            }
            catch (Exception ex) {
                ChatUtil.logger("&cEffect '" + effect + "' is not valid, please use a valid effect.");
            }
        }
    }
}
