package dev.risas.countach.tablist;

import dev.risas.countach.Countach;
import dev.risas.countach.model.lazarus.LazarusManager;
import dev.risas.countach.tablist.head.HeadManager;
import dev.risas.countach.utilities.ChatUtil;
import me.activated.core.api.ServerData;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TablistProvider implements TablistAdapter {

    private final Countach plugin;
    private final HeadManager headManager;
    private final LazarusManager lazarusManager;

    public TablistProvider(Countach plugin) {
        this.plugin = plugin;
        this.headManager = plugin.getHeadManager();
        this.lazarusManager = plugin.getLazarusManager();
    }

    @Override
    public List<String> getHeader(Player player) {
        return plugin.getTablistFile().getStringList("tablist.header")
                .stream().map(text -> ChatUtil.placeholder(player, text))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFooter(Player player) {
        return plugin.getTablistFile().getStringList("tablist.footer")
                .stream().map(text -> ChatUtil.placeholder(player, text))
                .collect(Collectors.toList());
    }

    @Override
    public Set<TablistLayout> getTablist(Player player) {
        Set<TablistLayout> layout = new HashSet<>();

        for (int i = 1; i <= 20; ++i) {
            layout.add(new TablistLayout(TablistColumn.LEFT, i)
                    .setText(ChatUtil.placeholder(player, replace(player, plugin.getTablistFile().getString("tablist.lines.left." + i + ".text"))))
                    .setHead(headManager.getHead(player, plugin.getTablistFile().getString("tablist.lines.left." + i + ".head"))));
            layout.add(new TablistLayout(TablistColumn.MIDDLE, i)
                    .setText(ChatUtil.placeholder(player, replace(player, plugin.getTablistFile().getString("tablist.lines.middle." + i + ".text"))))
                    .setHead(headManager.getHead(player, plugin.getTablistFile().getString("tablist.lines.middle." + i + ".head"))));
            layout.add(new TablistLayout(TablistColumn.RIGHT, i)
                    .setText(ChatUtil.placeholder(player, replace(player, plugin.getTablistFile().getString("tablist.lines.right." + i + ".text"))))
                    .setHead(headManager.getHead(player, plugin.getTablistFile().getString("tablist.lines.right." + i + ".head"))));
            layout.add(new TablistLayout(TablistColumn.FAR_RIGHT, i)
                    .setText(ChatUtil.placeholder(player, replace(player, plugin.getTablistFile().getString("tablist.lines.far-right." + i + ".text"))))
                    .setHead(headManager.getHead(player, plugin.getTablistFile().getString("tablist.lines.far-right." + i + ".head"))));
        }
        return layout;
    }

    private String replace(Player player, String text) {
        if (text.contains("-status>")) {
            String leftText = text.split("<")[0];
            String serverName = text
                    .replace(leftText, "")
                    .replace("<", "")
                    .replace("-status>", "");

            ServerData serverData = AquaCoreAPI.INSTANCE.getServerData(serverName);
            String placeholderPath = "<" + serverName + "-status>";

            if (serverData == null) return text
                    .replace(placeholderPath, "&cOffline");
            if (serverData.isWhitelisted()) return text
                    .replace(placeholderPath, "&eWhitelisted");
            if (serverData.isMaintenance()) return text
                    .replace(placeholderPath, "&cMaintenance");
            else return text.replace(placeholderPath, "&aOnline");
        }

        if (lazarusManager.getUserDataHCF().isConnected()) {
            text = text
                    .replace("<lazarus-hcf-kills>", String.valueOf(lazarusManager.getUserDataHCF().getKills(player.getUniqueId())))
                    .replace("<lazarus-hcf-deaths>", String.valueOf(lazarusManager.getUserDataHCF().getDeaths(player.getUniqueId())))
                    .replace("<lazarus-hcf-lives>", String.valueOf(lazarusManager.getUserDataHCF().getLives(player.getUniqueId())))
                    .replace("<lazarus-hcf-deathban>", lazarusManager.getUserDataHCF().getDeathban(player.getUniqueId()));
        }
        if (lazarusManager.getUserDataKitMap().isConnected()) {
            text = text
                    .replace("<lazarus-kitmap-kills>", String.valueOf(lazarusManager.getUserDataKitMap().getKills(player.getUniqueId())))
                    .replace("<lazarus-kitmap-deaths>", String.valueOf(lazarusManager.getUserDataKitMap().getDeaths(player.getUniqueId())))
                    .replace("<lazarus-kitmap-killstreak>", String.valueOf(lazarusManager.getUserDataKitMap().getKillStreak(player.getUniqueId())));
        }

        return text;
    }
}
