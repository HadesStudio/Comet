package dev.rage.comet.model.server;

import dev.rage.comet.model.server.impl.AquaCoreServerData;
import dev.rage.comet.model.server.impl.DefaultServerData;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class ServerDataManager {

    private final IServerData serverData;

    public ServerDataManager() {
        if (Bukkit.getPluginManager().getPlugin("AquaCore") != null) {
            this.serverData = new AquaCoreServerData();
        }
        else {
            this.serverData = new DefaultServerData();
        }
    }
}
