package dev.rage.comet.tablist.provider.impl;

import dev.rage.comet.tablist.provider.TablistProvider;
import dev.rage.comet.tablist.setup.TabEntry;
import dev.rage.comet.tablist.util.Skin;
import dev.rage.comet.utilities.Comet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersDisplayTablistProvider extends TablistProvider {

    public PlayersDisplayTablistProvider(Comet plugin) {
        super(plugin);
    }

    @Override
    public List<TabEntry> getLines(Player player) {
        List<TabEntry> entries = new ArrayList<>();
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (int i = 0; i < 80; i++) {
            int x = i % 4;
            int y = i / 4;

            if (players.size() <= i) continue;

            Player tabPlayer = players.get(i);
            if (tabPlayer == null) continue;

            entries.add(new TabEntry(x, y, tabPlayer.getDisplayName(), Skin.getPlayer(tabPlayer)));
        }

        return entries;
    }
}
