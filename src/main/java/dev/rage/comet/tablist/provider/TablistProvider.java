package dev.rage.comet.tablist.provider;

import dev.rage.comet.model.hcfcore.HCFCoreManager;
import dev.rage.comet.model.server.IServerData;
import dev.rage.comet.model.server.ServerDataManager;
import dev.rage.comet.tablist.adapter.TabAdapter;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.entity.Player;

public abstract class TablistProvider implements TabAdapter {

    protected final Comet plugin;
    protected final FileConfig tabFile;
    protected final HCFCoreManager hcfCoreManager;
    protected final ServerDataManager serverDataManager;

    public TablistProvider(Comet plugin) {
        this.plugin = plugin;
        this.tabFile = plugin.getTablistFile();
        this.hcfCoreManager = plugin.getHcfCoreManager();
        this.serverDataManager = plugin.getServerDataManager();
    }

    @Override
    public String getHeader(Player player) {
        return replace(player, plugin.getTablistFile().getString("tablist.header"));
    }

    @Override
    public String getFooter(Player player) {
        return replace(player, plugin.getTablistFile().getString("tablist.footer"));
    }

    protected String replace(Player player, String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

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

        if (text.contains("<player-name>")) {
            text = text.replace("<player-name>", player.getName());
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

        return ChatUtil.placeholder(player, text);
    }
}
