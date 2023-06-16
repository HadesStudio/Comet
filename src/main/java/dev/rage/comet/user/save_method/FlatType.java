package dev.rage.comet.user.save_method;

import dev.rage.comet.user.User;

import java.util.UUID;

public abstract class FlatType {

    public abstract void load(User user);

    public abstract void save(User user);

    public abstract User getUser(String name);

    public abstract User getUser(UUID uuid);
}
