package dev.astro.net.model.selector;

import dev.astro.net.utilities.Comet;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public void loadOrCreateServer(String name, ConfigurationSection section) {
        ServerSelector serverSelector = exists(name) ? getServerSelector(name) : new ServerSelector(name);
        serverSelector.setIcon(new ItemBuilder(section.getString(name + ".item.material"))
                .setName(section.getString(name + ".item.name"))
                .setLore(section.getStringList(name + ".item.description"))
                .setData(section.getInt(name + ".item.data"))
                .setSkullOwner(section.getString(name + ".item.head"))
                .setEnchanted(section.getBoolean(name + ".item.enchanted"))
                .build());
        serverSelector.setIconSlot(section.getInt(name + ".item.slot"));
        serverSelector.setServer(section.getString(name + ".server"));
        serverSelector.setCommands(section.getStringList(name + ".commands"));
        serverSelector.loadOrCreateFolder();

        FileConfig subServerFile = serverSelector.getSubServerFile();
        serverSelector.setMenuTitle(subServerFile.getString("menu.title"));
        serverSelector.setMenuRows(subServerFile.getInt("menu.rows"));

        serverSelector.getSubServerSelectors().clear();

        ConfigurationSection subServerSection = subServerFile.getConfiguration().getConfigurationSection("sub-servers");

        for (String subServerName : subServerSection.getKeys(false)) {
            loadOrCreateSubServer(serverSelector, subServerName, subServerSection);
        }

        if (!exists(name)) serverSelectors.put(name, serverSelector);
    }

    public void loadOrCreateSubServer(ServerSelector serverSelector, String subServerName, ConfigurationSection section) {
        SubServerSelector subServerSelector = new SubServerSelector(subServerName);
        subServerSelector.setIcon(new ItemBuilder(section.getString(subServerName + ".item.material"))
                .setName(section.getString(subServerName + ".item.name"))
                .setLore(section.getStringList(subServerName + ".item.description"))
                .setData(section.getInt(subServerName + ".item.data"))
                .setSkullOwner(section.getString(subServerName + ".item.head"))
                .setEnchanted(section.getBoolean(subServerName + ".item.enchanted"))
                .build());
        subServerSelector.setIconSlot(section.getInt(subServerName + ".item.slot"));
        subServerSelector.setSubServer(section.getString(subServerName + ".sub-server"));
        subServerSelector.setCommands(section.getStringList(subServerName + ".commands"));

        serverSelector.getSubServerSelectors().add(subServerSelector);
    }

    public void loadOrRefresh() {
        FileConfig serverSelectorFile = plugin.getServerSelectorFile();
        ConfigurationSection section = serverSelectorFile.getConfiguration().getConfigurationSection("servers");

        for (String serverName : section.getKeys(false)) {
            loadOrCreateServer(serverName, section);
        }

        for (ServerSelector serverSelector : serverSelectors.values()) {
            String serverName = serverSelector.getName();
            if (!section.contains(serverName)) serverSelectorNames.add(serverName);
        }

        serverSelectorNames.forEach(serverSelectors::remove);
        serverSelectorNames.clear();
    }
}
