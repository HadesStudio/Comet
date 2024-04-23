package dev.astro.net.model.hubselector;

import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;

@Getter
public class HubSelectorManager {

    private final Set<HubSelectorItem> hubSelectorItems = new HashSet<>();
    private final Comet plugin;

    private final String title;
    private final int size;

    public HubSelectorManager(Comet plugin) {
        this.plugin = plugin;

        this.title = ChatUtil.translate(plugin.getHubSelectorFile().getConfiguration().getString("title"));
        this.size = plugin.getHubSelectorFile().getConfiguration().getInt("size");

        loadItems();
    }

    public void loadItems() {
        for (String key : plugin.getHubSelectorFile().getConfiguration().getConfigurationSection("hubs").getKeys(false)) {
            ConfigurationSection configurationSection = plugin.getHubSelectorFile().getConfiguration().getConfigurationSection("hubs." + key);
            if (configurationSection == null)
                continue;

            hubSelectorItems.add(HubSelectorItem.of(configurationSection));
        }
    }

    public void loadOrRefresh() {
        this.hubSelectorItems.clear();
        loadItems();
    }
}




