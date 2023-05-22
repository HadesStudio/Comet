package dev.rage.comet.user;

import dev.rage.comet.model.cosmetics.impl.balloons.Balloon;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonHead;
import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter @Setter
public class User {

    private UUID uuid;
    private String name;
    private Balloon balloon;
    private Banner banner;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public boolean hasBalloon() {
        return this.balloon != null;
    }

    public void applyBallon(Player player, BalloonHead balloonHead) {
        if (hasBalloon()) removeBalloon();

        this.balloon = new Balloon(player, balloonHead.getHead());
        this.balloon.spawn();
    }

    public void removeBalloon() {
        this.balloon.remove();
        this.balloon = null;
    }

    public boolean hasBanner() {
        return this.banner != null;
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
