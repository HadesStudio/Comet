package dev.rage.comet.tablist.provider.impl;

import dev.rage.comet.tablist.provider.TablistProvider;
import dev.rage.comet.tablist.setup.TabEntry;
import dev.rage.comet.tablist.util.Skin;
import dev.rage.comet.utilities.Comet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CustomTablistProvider extends TablistProvider {

    public CustomTablistProvider(Comet plugin) {
        super(plugin);
    }

    @Override
    public List<TabEntry> getLines(Player player) {
        List<TabEntry> entries = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            entries.add(createEntry(player, TabOrdinalColumn.LEFT, i));
            entries.add(createEntry(player, TabOrdinalColumn.MIDDLE, i));
            entries.add(createEntry(player, TabOrdinalColumn.RIGHT, i));
            entries.add(createEntry(player, TabOrdinalColumn.FAR_RIGHT, i));
            //entries.add(new TabEntry(3, i, replace(player, tabFile.getString("tablist.lines.far-right." + i + ".text"))));
        }

        return entries;
    }

    private TabEntry createEntry(Player player, TabOrdinalColumn ordinalColumn, int slot) {
        TabEntry entry = new TabEntry();

        entry.setX(ordinalColumn.ordinalNumber);
        entry.setY(slot);

        String path = "tablist.lines." + ordinalColumn.name().toLowerCase() + "." + slot + ".";
        entry.setText(replace(player, tabFile.getString(path + "text")));

        String head = tabFile.getString(path + "head");

        if (head != null && !head.isEmpty()) {
            if (head.equalsIgnoreCase("PLAYER")) {
                entry.setSkin(Skin.getPlayer(player));
            } else {
                entry.setSkin(Skin.getSkin(head));
            }    
        }

        return entry;
    }

    @Getter @AllArgsConstructor
    private enum TabOrdinalColumn {
        LEFT(0),
        MIDDLE(1),
        RIGHT(2),
        FAR_RIGHT(3);

        private final int ordinalNumber;
    }
}
