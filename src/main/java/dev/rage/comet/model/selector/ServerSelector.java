package dev.rage.comet.model.selector;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @Setter
public class ServerSelector {

    private final String name;
    private ItemStack icon;
    private int iconSlot;
    private String server;
    private List<String> commands;

    public ServerSelector(String name) {
        this.name = name;
    }
}
