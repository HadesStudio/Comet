package dev.rage.comet.tablist.head;

import dev.rage.comet.model.server.IServerData;
import dev.rage.comet.model.server.ServerDataManager;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.tablist.Tablist;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Risas
 * Project: Comet
 * Date: 18-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class HeadManager {

    private final Comet plugin;
    private final Map<String, Head> heads;
    private final Set<String> headNames;

    private final ServerDataManager serverDataManager;

    public HeadManager(Comet plugin) {
        this.plugin = plugin;
        this.heads = new HashMap<>();
        this.headNames = new HashSet<>();
        this.serverDataManager = plugin.getServerDataManager();
        this.loadOrRefresh();
    }

    public boolean exists(String name) {
        return heads.containsKey(name);
    }

    public Head getHead(String name) {
        return heads.get(name);
    }

    public Head getHead(Player player, String text) {
        Head head = getHead(text);
        if (text.contains("<player-head>")) return this.getPlayerHead(player);
        if (text.contains("status-head")) return this.getServerStatusHead(text);
        if (head == null) return this.getDefault();
        return head;
    }

    public Head getPlayerHead(Player player) {
        return Tablist.getInstance().getTablistVersion().getHead(player);
    }

    public Head getServerStatusHead(String server) {
        String serverName = server
                .replace("<", "")
                .replace("-status-head>", "");
        IServerData serverData = serverDataManager.getServerData();
        if (serverData.isOffline(serverName)) return this.getHead("<offline-head>");
        else if (serverData.isWhitelisted(serverName)) return this.getHead("<whitelisted-head>");
        else if (serverData.isMaintenance(serverName)) return this.getHead("<maintenance-head>");
        else return this.getHead("<online-head>");
    }

    public Head getDefault() {
        return heads.getOrDefault("<default-head>", null);
    }

    public void loadOrCreate(String name, ConfigurationSection section) {
        String headName = "<" + name + "-head>";
        Head head = exists(headName) ? getHead(headName) : new Head();
        head.setName(headName);
        head.setValue(section.getString(name + ".value"));
        head.setSignature(section.getString(name + ".signature"));

        if (!exists(headName)) heads.put(headName, head);
    }

    public void loadOrRefresh() {
        FileConfig tablistHeadsFile = plugin.getTablistHeadsFile();
        ConfigurationSection section = tablistHeadsFile.getConfiguration().getConfigurationSection("heads");

        for (String headName : section.getKeys(false)) {
            loadOrCreate(headName, section);
        }

        for (Head head : heads.values()) {
            String headName = head.getName();
            String headSection = headName
                    .replace("-head", "")
                    .replace("<", "")
                    .replace(">", "");

            if (!section.contains(headSection)) {
                headNames.add(headName);
            }
        }

        headNames.forEach(heads::remove);
        headNames.clear();
    }
}
