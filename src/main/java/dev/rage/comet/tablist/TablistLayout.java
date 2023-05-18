package dev.rage.comet.tablist;

import dev.rage.comet.Comet;
import dev.rage.comet.tablist.head.Head;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(chain = true)
@Getter @Setter
public final class TablistLayout {

    private TablistColumn column;
    private int slot;
    private String text;
    private int ping;
    private Head head;

    public TablistLayout(TablistColumn column, int slot) {
        this(column, slot, "", -1);
    }

    public TablistLayout(TablistColumn column, int slot, String text) {
        this(column, slot, text, -1);
    }

    public TablistLayout(TablistColumn column, int slot, String text, int ping) {
        this(column, slot, text, ping, Comet.getInstance().getHeadManager().getDefault());
    }

    public TablistLayout(TablistColumn column, int slot, String text, Head head) {
        this(column, slot, text, -1, head);
    }

    public TablistLayout(TablistColumn column, int slot, String text, Head head, int ping) {
        this(column, slot, text, ping, head);
    }
}