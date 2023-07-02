package dev.rage.comet.user.database;

import dev.rage.comet.Comet;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public abstract class Database {

    public final Comet plugin;
    public final UserManager userManager;

    public abstract CompletableFuture<Void> load(User user);

    public abstract CompletableFuture<Void> save(User user);

    public abstract User getUserFromDB(String name);

    public abstract User getUserFromDB(UUID uuid);
}
