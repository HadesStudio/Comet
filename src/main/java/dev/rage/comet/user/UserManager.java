package dev.rage.comet.user;

import dev.rage.comet.utilities.Comet;
import dev.rage.comet.database.mongo.Mongo;
import dev.rage.comet.model.cosmetics.impl.balloons.Balloon;
import dev.rage.comet.user.database.Database;
import dev.rage.comet.user.database.impl.FlatFileDatabase;
import dev.rage.comet.user.database.impl.MongoDatabase;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {
    private final Comet plugin;
    private final Database database;
    private final Map<UUID, User> users;

    public UserManager(Comet plugin, @Nullable Mongo mongo) {
        this.plugin = plugin;
        this.users = new HashMap<>();

        if (mongo != null)
            this.database = new MongoDatabase(plugin, this, mongo);
        else
            this.database = new FlatFileDatabase(plugin, this);
    }

    public User getUser(UUID uuid) {
        return users.getOrDefault(uuid, null);
    }

    public void createUser(UUID uuid, String name) {
        User user = new User(uuid, name);
        user.load(this);
        users.put(uuid, user);
    }

    public void destroyUser(User user) {
        user.save(this);
        Balloon balloonModel = user.getBalloonModel();
        if (balloonModel != null) {
            balloonModel.remove();
        }
    }

    public void onDisable() {
        for (User user : users.values()) {
            destroyUser(user);
        }
    }
}
