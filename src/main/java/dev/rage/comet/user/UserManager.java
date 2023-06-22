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

    public User createUser(UUID uuid, String name) {
        User user = new User(uuid, name);

        user.load(this);

        users.put(uuid, user);

        return user;
    }

    public User createUser(Player player) {
        User user = new User(player.getUniqueId(), player.getName());

        user.load(this).join();

        users.put(player.getUniqueId(), user);
        return user;
    }

    public void destroyUser(User user, boolean delete) {
        user.save(this).thenAccept(unused -> {
            if (user.hasBalloonModel()) user.removeBalloon();
            //if (user.hasBanner()) user.removeBanner(player);
            if (delete) users.remove(user.getUuid());
        });
    }

    public void onDisable() {
        for (User user : users.values()) {
            destroyUser(user, false);
        }
    }
}
