package dev.rage.comet.user;

import dev.rage.comet.Comet;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {

    private final Comet plugin;
    private final Map<UUID, User> users;

    public UserManager(Comet plugin) {
        this.plugin = plugin;
        this.users = new HashMap<>();
    }

    public User getUser(UUID uuid) {
        return users.getOrDefault(uuid, null);
    }

    public void createUser(UUID uuid, String name) {
        User user = new User(uuid, name);
        users.put(uuid, user);
    }

    public void destroyUser(User user) {
        if (user.hasBalloon()) user.removeBalloon();
        users.remove(user.getUuid());
    }

    public void onDisable() {
        for (User user : users.values()) {
            if (user.hasBalloon()) user.removeBalloon();
        }
    }
}
