package dev.astro.net.model.server.impl;

import dev.astro.net.model.server.IServerData;

public class DefaultServerData implements IServerData {

    @Override
    public boolean isOffline(String server) {
        return true;
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
