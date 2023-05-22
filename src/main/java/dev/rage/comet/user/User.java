package dev.rage.comet.user;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.balloons.Balloon;
import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import dev.rage.comet.utilities.file.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Risas
 * Project: Neron
 * Date: 02-03-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter @Setter
public class User {

    private UUID uuid;
    private String name;
    private FileConfig fileConfig;
    private Balloon balloon;
    private Banner banner;

    public User(Comet plugin, UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.fileConfig = new FileConfig(plugin, "data/" + uuid.toString() + ".yml");
    }

    public String getBannerId() {
        return banner == null ? "" : banner.getId();
    }

    public boolean hasBanner() {
        return this.banner != null;
    }

    public boolean hasBanner(Banner banner) {
        return this.banner != null && this.banner.equals(banner);
    }

    public void applyBanner(Player player, Banner banner) {
        this.banner = banner;
        player.getInventory().setHelmet(banner.getItem());
    }

    public void removeBanner(Player player) {
        this.banner = null;
        player.getInventory().setHelmet(null);
    }
}
