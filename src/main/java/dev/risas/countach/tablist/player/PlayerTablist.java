package dev.risas.countach.tablist.player;

import dev.risas.countach.Countach;
import dev.risas.countach.tablist.Tablist;
import dev.risas.countach.tablist.TablistColumn;
import dev.risas.countach.tablist.TablistEntry;
import dev.risas.countach.tablist.TablistLayout;
import dev.risas.countach.tablist.player.module.PlayerVersion;
import dev.risas.countach.tablist.utilities.ClientUtil;
import dev.risas.countach.utilities.ChatUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class PlayerTablist {

    private final Countach plugin;
    private final Player player;
    private Scoreboard scoreboard;
    private int lastHeaderFooter;

    private final Set<TablistEntry> currentEntrySet = new HashSet<>();

    public PlayerTablist(Countach plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        if (!this.player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            scoreboard = player.getScoreboard();
        }

        player.setScoreboard(scoreboard);

        this.setup();

        Team playerTeam = player.getScoreboard().getTeam("\\u000181");

        if (playerTeam == null) {
            playerTeam = player.getScoreboard().registerNewTeam("\\u000181");
        }

        playerTeam.addEntry(player.getName());

        for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
            Team onlineTeam = onlinePlayers.getScoreboard().getTeam("\\u000181");

            if (onlineTeam == null) {
                onlineTeam = onlinePlayers.getScoreboard().registerNewTeam("\\u000181");
            }

            onlineTeam.addEntry(player.getName());
            onlineTeam.addEntry(onlinePlayers.getName());
            playerTeam.addEntry(onlinePlayers.getName());
            playerTeam.addEntry(player.getName());
        }
    }

    public static String[] splitText(String text, int raw) {
        int length = text.length();

        if (length > 16) {
            String suffix;
            String prefix = text.substring(0, 16);

            if (prefix.charAt(15) == '§' || prefix.charAt(15) == '&') {
                prefix = prefix.substring(0, 15);
                suffix = text.substring(15, length);
            }
            else if (prefix.charAt(14) == '§' || prefix.charAt(14) == '&') {
                prefix = prefix.substring(0, 14);
                suffix = text.substring(14, length);
            }
            else {
                suffix = ChatColor.getLastColors(ChatUtil.translate(prefix)) + text.substring(16, length);
            }

            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }

            return new String[] { prefix, suffix };
        }
        else {
            return new String[] { text, "" };
        }
    }

    private void setup() {
        if (PlayerVersionManager.getPlayerVersion(player) == null) return;

        for (int slot = 1; slot <= (PlayerVersionManager.getPlayerVersion(player) == PlayerVersion.v1_7 ? 60 : 80); slot++) {
            TablistColumn column = TablistColumn.getColumn(player, slot);

            if (column != null) {
                TablistEntry entry = Tablist.getInstance().getTablistVersion().createEntry(
                        this,
                        "0" + (slot > 9 ? slot : "0" + slot) + "|Tablist",
                        column,
                        column.getSlot(player, slot),
                        slot
                );

                if (PlayerVersionManager.getPlayerVersion(player) == PlayerVersion.v1_7) {
                    Team team = player.getScoreboard().getTeam(ClientUtil.NAMES.get(slot - 1));

                    if (team != null) team.unregister();

                    team = player.getScoreboard().registerNewTeam(ClientUtil.NAMES.get(slot - 1));
                    team.setPrefix("");
                    team.setSuffix("");

                    team.addEntry(ClientUtil.ENTRY.get(slot - 1));
                }

                currentEntrySet.add(entry);
            }
        }
    }

    public void update() {
        if (PlayerVersionManager.getPlayerVersion(player) == null) return;

        if (PlayerVersionManager.getPlayerVersion(player) != PlayerVersion.v1_7) {
            List<String> header = Tablist.getInstance().getTablistAdapter().getHeader(player);
            List<String> footer = Tablist.getInstance().getTablistAdapter().getFooter(player);

            int headerFooter = header.hashCode() + footer.hashCode();

            if (headerFooter != lastHeaderFooter) {
                lastHeaderFooter = headerFooter;
                Tablist.getInstance().getTablistVersion().updateHeaderAndFooter(player, header, footer);
            }
        }

        Set<TablistEntry> lastSet = new HashSet<>(currentEntrySet);

        for (TablistLayout layout : Tablist.getInstance().getTablistAdapter().getTablist(player)) {
            TablistEntry entry = getEntry(layout.getColumn(), layout.getSlot());

            if (entry != null) {
                lastSet.remove(entry);

                Tablist.getInstance().getTablistVersion().updateText(this, entry, layout.getText());
                Tablist.getInstance().getTablistVersion().updateLatency(this, entry, layout.getPing());

                if (PlayerVersionManager.getPlayerVersion(this.player) == PlayerVersion.v1_7) continue;

                Tablist.getInstance().getTablistVersion().updateSkin(this, entry, layout.getHead());
            }
        }
        for (TablistEntry entry : lastSet) {
            Tablist.getInstance().getTablistVersion().updateText(this, entry, "");
            Tablist.getInstance().getTablistVersion().updateLatency(this, entry, 0);

            if (PlayerVersionManager.getPlayerVersion(this.player) != PlayerVersion.v1_7) {
                Tablist.getInstance().getTablistVersion().updateSkin(this, entry, plugin.getHeadManager().getDefault());
            }
        }
        lastSet.clear();
    }

    public static Team getOrCreateTeam(Scoreboard scoreboard, String name) {
        Team team = scoreboard.getTeam(name);

        if (team == null) {
            return scoreboard.registerNewTeam(name);
        }

        return team;
    }

    public TablistEntry getEntry(TablistColumn column, Integer slot) {
        return currentEntrySet.stream().filter(entry -> entry.getColumn() == column && entry.getSlot() == slot).findFirst().orElse(null);
    }
}

