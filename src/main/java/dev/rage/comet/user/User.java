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

@Getter @Setter
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

    public boolean hasBalloon() {
        return this.balloon != null;
    }

    /*public void applyBalloon(Player player, BalloonHead balloonHead) {
        if (hasBalloon()) removeBalloon();

        this.balloon = new Balloon(player, balloonHead.getHead());
        this.balloon.spawn();
    }

    public void removeBalloon() {
        this.balloon.remove();
        this.balloon = null;
    }*/

    public boolean hasMascot() {
        return this.mascot != null;
    }

    /*public void applyMascot(Comet plugin, Player player, Body body) {
        if (hasMascot()) removeMascot(plugin);


        this.mascot = new Mascot(plugin, player, body);
        plugin.getMascotManager().getMascots().put(uuid, mascot);
    }

    public void removeMascot(Comet plugin) {
        this.mascot = null;
        plugin.getMascotManager().getMascots().get(uuid).die();
    }*/

    public boolean hasBanner() {
        return this.banner != null;
    }

    /*public void applyBanner(Player player, Banner banner) {
        this.banner = banner;
        player.getInventory().setHelmet(banner.getItem());
    }

    public void removeBanner(Player player) {
        this.banner = null;
        player.getInventory().setHelmet(null);
    }*/

    public boolean hasOutfit() {
        return this.outfit != null;
    }

    public boolean hasParticle() {
        return this.particle != null;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
