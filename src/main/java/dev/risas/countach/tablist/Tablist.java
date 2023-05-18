package dev.risas.countach.tablist;


import dev.risas.countach.Countach;
import dev.risas.countach.tablist.player.PlayerTablist;
import dev.risas.countach.tablist.player.PlayerVersionManager;
import dev.risas.countach.tablist.version.TablistVersion;
import dev.risas.countach.tablist.version.impl.Tablist_v1_8_R3;
import dev.risas.countach.utilities.ChatUtil;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Tablist {

    @Getter
    private static Tablist instance;

    private final Countach plugin;
    private final TablistAdapter tablistAdapter;
    private final Map<UUID, PlayerTablist> playerTablist;
    private TablistThread tablistThread;
    private TablistVersion tablistVersion;
    private final PlayerVersionManager playerVersionManager;

    public Tablist(@NonNull Countach plugin, TablistAdapter adapter) {
        instance = this;

        this.plugin = plugin;
        this.tablistAdapter = adapter;
        this.playerTablist = new ConcurrentHashMap<>();
        this.playerVersionManager = new PlayerVersionManager();

        this.setupTablistVersion();
        this.setup();
    }

    private void setupTablistVersion() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            this.tablistVersion = new Tablist_v1_8_R3(plugin);
        }
        else {
            ChatUtil.logger("&c[Tablist] ProtocolLib not found, disabling Tablist");
        }
    }

    private void setup() {
        if (this.tablistVersion == null) return;

        this.plugin.getServer().getPluginManager().registerEvents(new TablistListener(this.plugin), this.plugin);

        if (this.tablistThread != null) {
            this.tablistThread.stop();
            this.tablistThread = null;
        }

        this.tablistThread = new TablistThread(this);
    }

    public void disable() {
        if (this.tablistThread != null) {
            this.tablistThread.stop();
            this.tablistThread = null;
        }

        for (UUID uuid : getPlayerTablist().keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) continue;

            getPlayerTablist().remove(uuid);
        }
    }
}
