package dev.rage.comet.model.cosmetics.impl.balloons;

import com.cryptomorin.xseries.XMaterial;
import dev.rage.comet.Comet;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

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

        plugin.getServer().getPluginManager().registerEvents(new BalloonListener(this), plugin);

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
        balloonHead.setHead(new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial())
                .setName(section.getString(name + ".displayName"))
                .setData(XMaterial.PLAYER_HEAD.getData())
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
}
