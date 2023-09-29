package dev.rage.comet.user.database;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
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
