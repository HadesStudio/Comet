package dev.astro.net.model.server.impl;

import dev.astro.net.model.server.IServerData;
import services.plasma.helium.api.HeliumAPI;

public class HeliumServerData implements IServerData {

    @Override
    public boolean isOffline(String server) {
        return !HeliumAPI.INSTANCE.isServerOnline(server);
    }

    @Override
    public boolean isWhitelisted(String server) {
        return false;
    }

    @Override
    public boolean isMaintenance(String server) {
        return false;
    }
}
