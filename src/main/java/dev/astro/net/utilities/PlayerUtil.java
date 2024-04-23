package dev.astro.net.utilities;

import com.cryptomorin.xseries.XSound;
import fr.mrmicky.fastparticles.ParticleType;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Comet
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
            ParticleType particleType = ParticleType.of(effect);
            try {
                particleType.spawn(player.getWorld(), player.getLocation(), 1, 1);
            } catch (Exception ex) {
                ChatUtil.logger("&cEffect '" + effect + "' is not valid, please use a valid effect.");
            }
        }
    }
}