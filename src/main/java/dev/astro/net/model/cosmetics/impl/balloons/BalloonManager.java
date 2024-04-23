package dev.astro.net.model.cosmetics.impl.balloons;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.balloons.listener.BalloonListener;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class BalloonManager {

    private final FileConfig balloonsFile;
    private final Map<String, BalloonHead> balloonHeads;
    private final List<String> balloonHeadNames;

    public BalloonManager(Comet plugin) {
        this.balloonsFile = plugin.getBalloonsFile();
        this.balloonHeads = new HashMap<>();
        this.balloonHeadNames = new ArrayList<>();

        CometPlugin.getPlugin().getServer().getPluginManager().registerEvents(new BalloonListener(plugin, this), CometPlugin.getPlugin());

        this.loadOrRefresh();
    }

    public BalloonHead getBalloonHead(String name) {
        return balloonHeads.getOrDefault(name, null);
    }

    public boolean exists(String name) {
        return balloonHeads.containsKey(name);
    }

    public void loadOrCreate(String name, ConfigurationSection section) {
        BalloonHead balloonHead = exists(name) ? getBalloonHead(name) : new BalloonHead(name);
        balloonHead.setHead(new ItemBuilder(com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.parseMaterial())
                .setName(section.getString(name + ".displayName"))
                .setData(com.cryptomorin.xseries.XMaterial.PLAYER_HEAD.getData())
                .setSkullOwner(section.getString(name + ".texture"))
                .build());
        if (!exists(name)) balloonHeads.put(name, balloonHead);
    }

    public void loadOrRefresh() {
        ConfigurationSection section = balloonsFile.getConfiguration().getConfigurationSection("balloons");

        for (String balloonHeadId : section.getKeys(false)) {
            loadOrCreate(balloonHeadId, section);
        }

        for (BalloonHead balloonHead : balloonHeads.values()) {
            String balloonHeadId = balloonHead.getId();
            if (!section.contains(balloonHeadId)) balloonHeadNames.add(balloonHeadId);
        }

        balloonHeadNames.forEach(balloonHeads::remove);
        balloonHeadNames.clear();
    }

    public boolean isBalloon(Entity entity) {
        return entity.hasMetadata(CometPlugin.getPlugin().getName()) && (entity instanceof ArmorStand || entity instanceof Bat);
    }
}
