package dev.rage.comet.model.server.impl;

import dev.rage.comet.model.server.IServerData;
import dev.rage.helium.api.HeliumAPI;

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
