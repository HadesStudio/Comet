package dev.astro.net.model.server;

public interface IServerData {

    boolean isOffline(String server);
    boolean isWhitelisted(String server);
    boolean isMaintenance(String server);
}
