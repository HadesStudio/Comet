package dev.astro.net.user.database;

import dev.astro.net.utilities.Comet;
import dev.astro.net.user.User;
import dev.astro.net.user.UserManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class Database {

    public final Comet plugin;
    public final UserManager userManager;

    public abstract void load(User user);

    public abstract void save(User user);

    public abstract User getUserFromDB(String name);

    public abstract User getUserFromDB(UUID uuid);

    public abstract List<User> getUsersFromDB();
}
