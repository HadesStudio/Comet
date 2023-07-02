package dev.rage.comet.tablist;

import dev.rage.comet.tablist.head.Head;
import dev.rage.comet.tablist.player.PlayerTablist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter @Setter
public class TablistEntry {
    private String id;
    private UUID uuid;
    private String text;
    private PlayerTablist tablist;
    private Head head;
    private TablistColumn column;
    private int slot, rawSlot, latency;
}