package dev.rage.comet.user.save_method;

import dev.rage.comet.Comet;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class FlatType {

    public final Comet plugin;
    public final UserManager userManager;

    public abstract void load(User user);

    public abstract void save(User user);

    public abstract User getUser(String name);

    public abstract User getUser(UUID uuid);
}
