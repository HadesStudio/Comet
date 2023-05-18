package dev.risas.countach.tablist.version;

import dev.risas.countach.tablist.TablistColumn;
import dev.risas.countach.tablist.TablistEntry;
import dev.risas.countach.tablist.head.Head;
import dev.risas.countach.tablist.player.PlayerTablist;
import org.bukkit.entity.Player;

import java.util.List;

public interface TablistVersion {

    TablistEntry createEntry(PlayerTablist playerTablist, String string, TablistColumn column, Integer slot, Integer rawSlot);
    void updateText(PlayerTablist playerTablist, TablistEntry tablistEntry, String text);
    void updateLatency(PlayerTablist playerTablist, TablistEntry tablistEntry, Integer latency);
    void updateSkin(PlayerTablist playerTablist, TablistEntry tablistEntry, Head head);
    void updateHeaderAndFooter(Player player, List<String> header, List<String> footer);
    Head getHead(Player player);
}