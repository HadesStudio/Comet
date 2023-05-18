package dev.rage.comet.tablist.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ClientUtil {

    public final List<String> NAMES = new ArrayList<>();
    public final List<String> ENTRY = new ArrayList<>();

    static {
        for (int i = 0; i < 80; i++) {
            NAMES.add((i < 10 ? "\\u00010" : "\\u0001") + i);
        }

        for (int i = 1; i <= 15; i++) {
            String entry = ChatColor.values()[i].toString();
            ENTRY.add(ChatColor.RED + entry);
            ENTRY.add(ChatColor.GREEN + entry);
            ENTRY.add(ChatColor.DARK_RED + entry);
            ENTRY.add(ChatColor.DARK_GREEN + entry);
            ENTRY.add(ChatColor.BLUE + entry);
            ENTRY.add(ChatColor.DARK_BLUE + entry);
        }
    }
}
