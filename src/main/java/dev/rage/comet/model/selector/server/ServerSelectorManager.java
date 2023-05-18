package dev.rage.comet.model.selector.server;

import dev.rage.comet.Comet;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

@Getter
public class ServerSelectorManager {

    private final Comet plugin;
    private final Map<String, ServerSelector> serverSelectors;
    private final Set<String> serverSelectorNames;

    public ServerSelectorManager(Comet plugin) {
        this.plugin = plugin;
        this.serverSelectors = new HashMap<>();
        this.serverSelectorNames = new HashSet<>();
        this.loadOrRefresh();
    }

    public Collection<ServerSelector> getServerSelectors() {
        return serverSelectors.values();
    }

    public boolean exists(String name) {
        return serverSelectors.containsKey(name);
    }

    public ServerSelector getServerSelector(String name) {
        return serverSelectors.get(name);
    }

    public void loadOrCreate(String name, ConfigurationSection section) {
        ServerSelector serverSelector = exists(name) ? getServerSelector(name) : new ServerSelector(name);
        serverSelector.setIcon(new ItemBuilder(section.getString(name + ".item.material"))
                .setName(section.getString(name + ".item.name"))
                .setLore(section.getStringList(name + ".item.description"))
                .setData(section.getInt(name + ".item.data"))
                .setSkullOwner(section.getString(name + ".item.head"))
                .setEnchanted(section.getBoolean(name + ".item.enchanted"))
                .build());
        serverSelector.setIconSlot(section.getInt(name+ ".item.slot"));
        serverSelector.setServer(section.getString(name + ".server"));
        serverSelector.setCommands(section.getStringList(name + ".commands"));

        if (!exists(name)) serverSelectors.put(name, serverSelector);
    }

    public void loadOrRefresh() {
        FileConfig serverSelectorFile = plugin.getServerSelectorFile();
        ConfigurationSection section = serverSelectorFile.getConfiguration().getConfigurationSection("servers");

        for (String serverName : section.getKeys(false)) {
            loadOrCreate(serverName, section);
        }

        for (ServerSelector serverSelector : serverSelectors.values()) {
            String serverName = serverSelector.getName();
            if (!section.contains(serverName)) serverSelectorNames.add(serverName);
        }

        serverSelectorNames.forEach(serverSelectors::remove);
        serverSelectorNames.clear();
    }
}
