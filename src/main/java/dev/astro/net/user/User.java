package dev.astro.net.user;

import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.balloons.Balloon;
import dev.astro.net.model.cosmetics.impl.balloons.BalloonHead;
import dev.astro.net.model.cosmetics.impl.banners.Banner;
import dev.astro.net.model.cosmetics.impl.gadgets.Gadget;
import dev.astro.net.model.cosmetics.impl.mascots.impl.Body;
import dev.astro.net.model.cosmetics.impl.mascots.impl.Mascot;
import dev.astro.net.model.cosmetics.impl.outfits.entries.Outfit;
import dev.astro.net.model.cosmetics.impl.particles.Particle;
import dev.astro.net.utilities.JavaUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter @Setter
public class User {

    private final UUID uuid;
    private final String name;
    private long parkourTime;
    private BalloonHead balloon;
    private Banner banner;
    private Body mascot;
    private Outfit outfit;
    private Particle particle;
    private Gadget gadget;
    private Balloon balloonModel;
    private Mascot mascotModel;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getParkourTimeFormatted() {
        return JavaUtil.formatMillis(this.parkourTime);
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

    public boolean hasGadget() {
        return this.gadget != null;
    }

    public void applyBalloon(Comet plugin, Player player, BalloonHead balloonHead) {
        if (hasBalloonModel()) removeBalloon();

        if (this.balloon == null || !this.balloon.getId().equals(balloonHead.getId()))
            this.balloon = balloonHead;

        this.balloonModel = new Balloon(plugin, player, balloonHead.getHead());
        this.balloonModel.spawn();
    }

    public void applyMascot(Comet plugin, Player player, Body body) {
        if (hasMascotModel()) removeMascot();

        if (this.mascot == null || !this.mascot.getName().equals(body.getName()))
            this.mascot = body;

        this.mascotModel = new Mascot(plugin, player, body);
        plugin.getMascotManager().getMascots().put(uuid, mascotModel);
    }

    public void applyBanner(Player player, Banner banner) {
        if (hasBannerModel()) removeBanner(player);

        if (this.banner == null || !this.banner.getId().equals(banner.getId()))
            this.banner = banner;

        this.banner = banner;
        player.getInventory().setHelmet(banner.getItem());
    }

    public void applyGadget(Player player, Gadget gadget) {
        if (hasGadget()) removeGadget(player);

        this.gadget = gadget;
        gadget.onUsage(player);
    }

    public void removeBalloon() {
        if (hasBalloonModel()) {
            this.balloonModel.remove();
            this.balloonModel = null;
            this.balloon = null;
        }
    }

    public void removeMascot() {
        if (mascot != null) {
            this.mascot = null;
            if (this.mascotModel != null) this.mascotModel.die();
        }
    }

    public void removeBanner(Player player) {
        if (banner != null) {
            this.banner = null;
            player.getInventory().setHelmet(null);
        }
    }

    public void removeParticle() {
        if (particle != null) {
            this.particle = null;
        }
    }

    public void removeOutfit(Player player) {
        player.getInventory().setArmorContents(null);
    }

    public void removeGadget(Player player) {
        if (gadget != null) {
            gadget.onRemoval(player);
            this.gadget = null;
        }
    }

    public void load(UserManager userManager) {
        userManager.getDatabase().load(this);
    }

    public void save(UserManager userManager) {
        userManager.getDatabase().save(this);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
