package dev.astro.net.model.hubselector;

import com.cryptomorin.xseries.XMaterial;
import dev.astro.net.utilities.item.ItemBuilder;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class HubSelectorItem {

    private ItemStack itemStack;

    private String displayName;
    private List<String> lore;

    private Material material;

    private String serverName;
    private String permission;

    private int slot;

    public HubSelectorItem(String name) {
        this.serverName = name;
    }

    public static HubSelectorItem of(ConfigurationSection section) {
        HubSelectorItem hubSelectorItem = new HubSelectorItem(section.getName());
        hubSelectorItem.displayName = section.getString("item.displayName");
        hubSelectorItem.lore = section.getStringList("item.lore");
        hubSelectorItem.material = XMaterial.matchXMaterial(section.getString("item.material"))
                .map(XMaterial::parseMaterial)
                .orElse(Material.BEDROCK);

        hubSelectorItem.permission = section.getString("permission");
        hubSelectorItem.slot = section.getInt("slot");

        hubSelectorItem.itemStack = new ItemBuilder(hubSelectorItem.material)
                .setName(hubSelectorItem.displayName)
                .setLore(hubSelectorItem.lore)
                .build();

        return hubSelectorItem;
    }

}
