package dev.rage.comet.model.selector.subserver;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @Setter
public class SubServerSelector {

    private final String name;
    private ItemStack icon;
    private int iconSlot;
    private String subServer, server;
    private List<String> commands;

    public SubServerSelector(String name) {
        this.name = name;
    }
}
