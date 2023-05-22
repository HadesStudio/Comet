package dev.rage.comet.user;

import dev.rage.comet.Comet;
import dev.rage.comet.user.impl.UserFlatFile;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {

    private final Comet plugin;

    private final IUser user;
    private final Map<UUID, User> users;

    public UserManager(Comet plugin) {
        this.plugin = plugin;
        this.users = new HashMap<>();
        this.user = new UserFlatFile(plugin.getBannerManager());
    }

    public User getUser(UUID uuid) {
        return users.getOrDefault(uuid, null);
    }

    public User createUser(UUID uuid, String name) {
        User user = new User(plugin, uuid, name);
        users.put(uuid, user);
        return user;
    }

    public void saveUser(User user, boolean async) {
        if (async) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> this.user.saveUser(user));
        }
        else {
            this.user.saveUser(user);
        }
    }

    public void loadUser(User user, boolean byName) {
        this.user.loadUser(user, byName);
    }

    public void destroyUser(User user) {
        users.remove(user.getUuid());
    }

    public void onDisable() {
        for (User user : users.values()) {
            if (user != null) saveUser(user, false);
        }
    }
}
