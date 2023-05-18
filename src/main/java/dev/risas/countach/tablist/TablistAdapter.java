package dev.risas.countach.tablist;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface TablistAdapter {

    List<String> getFooter(Player player);
    List<String> getHeader(Player player);
    Set<TablistLayout> getTablist(Player player);
}
