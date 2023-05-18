package dev.risas.countach.tablist;

import dev.risas.countach.tablist.head.Head;
import dev.risas.countach.tablist.player.PlayerTablist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

@AllArgsConstructor
@Getter @Setter
public class TablistEntry {

    private String id;
    private OfflinePlayer offlinePlayer;
    private String text;
    private PlayerTablist tablist;
    private Head head;
    private TablistColumn column;
    private int slot, rawSlot, latency;
}
