package dev.rage.comet.tablist;

import dev.rage.comet.Comet;
import dev.rage.comet.model.hcfcore.HCFCoreManager;
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
    private final HCFCoreManager hcfCoreManager;
    private final ServerDataManager serverDataManager;

    public TablistProvider(Comet plugin) {
        this.plugin = plugin;
        this.headManager = plugin.getHeadManager();
        this.hcfCoreManager = plugin.getHcfCoreManager();
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

        if (hcfCoreManager.getHcf() != null && hcfCoreManager.getHcf().isConnected()) {
            text = text
                    .replace("<hcfcore-hcf-kills>", String.valueOf(hcfCoreManager.getHcf().getKills(player.getUniqueId())))
                    .replace("<hcfcore-hcf-deaths>", String.valueOf(hcfCoreManager.getHcf().getDeaths(player.getUniqueId())))
                    .replace("<hcfcore-hcf-lives>", String.valueOf(hcfCoreManager.getHcf().getLives(player.getUniqueId())))
                    .replace("<hcfcore-hcf-deathban>", hcfCoreManager.getHcf().getDeathban(player.getUniqueId()));
        }
        if (hcfCoreManager.getKitmap() != null && hcfCoreManager.getKitmap().isConnected()) {
            text = text
                    .replace("<hcfcore-kitmap-kills>", String.valueOf(hcfCoreManager.getKitmap().getKills(player.getUniqueId())))
                    .replace("<hcfcore-kitmap-deaths>", String.valueOf(hcfCoreManager.getKitmap().getDeaths(player.getUniqueId())))
                    .replace("<hcfcore-kitmap-killstreak>", String.valueOf(hcfCoreManager.getKitmap().getKillStreak(player.getUniqueId())));
        }

        return text;
    }
}
