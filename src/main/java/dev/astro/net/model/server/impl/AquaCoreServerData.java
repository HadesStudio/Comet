package dev.astro.net.model.server.impl;

import dev.astro.net.model.server.IServerData;
import me.activated.core.api.ServerData;
import me.activated.core.plugin.AquaCoreAPI;

public class AquaCoreServerData implements IServerData {

    @Override
    public boolean isOffline(String server) {
        return getServerData(server) == null;
    }

    @Override
    public boolean isWhitelisted(String server) {
        return getServerData(server).isWhitelisted();
    }

    @Override
    public boolean isMaintenance(String server) {
        return getServerData(server).isMaintenance();
    }

    public ServerData getServerData(String name) {
        return AquaCoreAPI.INSTANCE.getServerData(name);
    }
}
