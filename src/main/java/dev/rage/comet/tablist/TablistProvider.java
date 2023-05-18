package dev.rage.comet.tablist;

import dev.rage.comet.Comet;
import dev.rage.comet.model.lazarus.LazarusManager;
import dev.rage.comet.model.server.IServerData;
import dev.rage.comet.model.server.ServerDataManager;
import dev.rage.comet.tablist.head.HeadManager;
import dev.rage.comet.utilities.ChatUtil;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TablistProvider implements TablistAdapter {

    private final Comet plugin;
    private final HeadManager headManager;
    private final LazarusManager lazarusManager;
    private final ServerDataManager serverDataManager;

    public TablistProvider(Comet plugin) {
        this.plugin = plugin;
        this.headManager = plugin.getHeadManager();
        this.lazarusManager = plugin.getLazarusManager();
        this.serverDataManager = plugin.getServerDataManager();
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
            layout.add(new TablistLayout(plugin, TablistColumn.LEFT, i)
                    .setText(ChatUtil.placeholder(player, replace(player, plugin.getTablistFile().getString("tablist.lines.left." + i + ".text"))))
                    .setHead(headManager.getHead(player, plugin.getTablistFile().getString("tablist.lines.left." + i + ".head"))));
            layout.add(new TablistLayout(plugin, TablistColumn.MIDDLE, i)
                    .setText(ChatUtil.placeholder(player, replace(player, plugin.getTablistFile().getString("tablist.lines.middle." + i + ".text"))))
                    .setHead(headManager.getHead(player, plugin.getTablistFile().getString("tablist.lines.middle." + i + ".head"))));
            layout.add(new TablistLayout(plugin, TablistColumn.RIGHT, i)
                    .setText(ChatUtil.placeholder(player, replace(player, plugin.getTablistFile().getString("tablist.lines.right." + i + ".text"))))
                    .setHead(headManager.getHead(player, plugin.getTablistFile().getString("tablist.lines.right." + i + ".head"))));
            layout.add(new TablistLayout(plugin, TablistColumn.FAR_RIGHT, i)
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

            IServerData serverData = serverDataManager.getServerData();
            String placeholderPath = "<" + serverName + "-status>";

            if (serverData.isOffline(serverName)) return text
                    .replace(placeholderPath, "&cOffline");
            else if (serverData.isWhitelisted(serverName)) return text
                    .replace(placeholderPath, "&eWhitelisted");
            else if (serverData.isMaintenance(serverName)) return text
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
