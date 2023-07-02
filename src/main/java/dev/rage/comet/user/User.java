package dev.rage.comet.user;

import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.balloons.Balloon;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonHead;
import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import dev.rage.comet.model.cosmetics.impl.mascots.impl.Body;
import dev.rage.comet.model.cosmetics.impl.mascots.impl.Mascot;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.Outfit;
import dev.rage.comet.model.cosmetics.impl.particles.Particle;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class User {

    private final UUID uuid;
    private final String name;
    /*private String balloon, banner, mascot, outfit, particle;*/
    private BalloonHead balloon;
    private Banner banner;
    private Body mascot;
    private Outfit outfit;
    private Particle particle;

    private Balloon balloonModel;
    private Mascot mascotModel;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public boolean hasBalloonModel() {
        return this.balloonModel != null;
    }

    public boolean hasMascotModel() {
        return this.mascotModel != null;
    }

    public boolean hasBannerModel() {
        return this.getPlayer() != null && this.getPlayer().getInventory().getHelmet() != null;
    }

    /*
        I will keep it with the Void supply as I may have problems when switching lobbies directly
        when swapping lobbies directly
    */
    public void load(UserManager userManager) {
        userManager.getDatabase().load(this);
    }

    public void save(UserManager userManager) {
        userManager.getDatabase().save(this);
    }

    public void applyBalloon(Comet plugin, Player player, BalloonHead balloonHead) {
        if (hasBalloonModel()) removeBalloon();

        if (this.balloon == null || !this.balloon.getId().equals(balloonHead.getId()))
            this.balloon = balloonHead;

        this.balloonModel = new Balloon(plugin, player, balloonHead.getHead());
        this.balloonModel.spawn();
    }

    public void removeBalloon() {
        this.balloonModel.remove();
        this.balloonModel = null;
        this.balloon = null;
    }

    public void applyMascot(Comet plugin, Player player, Body body) {
        if (hasMascotModel()) removeMascot();

        if (this.mascot == null || !this.mascot.getName().equals(body.getName()))
            this.mascot = body;

        this.mascotModel = new Mascot(plugin, player, body);
        plugin.getMascotManager().getMascots().put(uuid, mascotModel);
    }

    public void removeMascot() {
        this.mascot = null;

        if (this.mascotModel != null) this.mascotModel.die();
    }

    public void applyBanner(Player player, Banner banner) {
        if (hasBannerModel()) removeBanner(player);

        if (this.banner == null || !this.banner.getId().equals(banner.getId()))
            this.banner = banner;

        this.banner = banner;
        player.getInventory().setHelmet(banner.getItem());
    }

    public void removeBanner(Player player) {
        this.banner = null;
        player.getInventory().setHelmet(null);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
