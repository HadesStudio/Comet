package dev.rage.comet.user;

import dev.rage.comet.Comet;
import dev.rage.comet.database.mongo.Mongo;
import dev.rage.comet.user.save_method.FlatType;
import dev.rage.comet.user.save_method.impl.FlatFileMethod;
import dev.rage.comet.user.save_method.impl.FlatMongoMethod;
import lombok.Getter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {

    private final Comet plugin;

    private final FlatType flatType;

    private final Map<UUID, User> users;

    public UserManager(Comet plugin, @Nullable Mongo mongo) {
        this.plugin = plugin;
        this.users = new HashMap<>();

        if (mongo != null)
            this.flatType = new FlatMongoMethod(plugin, this, mongo);
        else
            this.flatType = new FlatFileMethod(plugin, this);
    }

    public User getUser(UUID uuid) {
        return users.getOrDefault(uuid, null);
    }

    public void createUser(UUID uuid, String name) {
        User user = new User(uuid, name);

        user.load(this);

        users.put(uuid, user);
    }

    public void destroyUser(User user, boolean delete) {
        user.save(this).thenAccept(unused -> {
            if (delete) users.remove(user.getUuid());
        });
    }

    public void onDisable() {
        for (User user : users.values()) {
            destroyUser(user, false);
        }
    }
}
