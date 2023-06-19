package dev.rage.comet.user;

import com.mongodb.client.MongoCollection;
import dev.rage.comet.Comet;
import dev.rage.comet.database.mongo.Mongo;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {

    private final Comet plugin;

    private MongoCollection<Document> collection;

    private final Map<UUID, User> users;

    public UserManager(Comet plugin, @Nullable Mongo mongo) {
        this.plugin = plugin;
        this.users = new HashMap<>();

        if (mongo != null && mongo.isConnected()) {
            this.collection = mongo.getDatabase().getCollection("profiles");
        }
    }

    public User getUser(UUID uuid) {
        return users.getOrDefault(uuid, null);
    }

    public void createUser(UUID uuid, String name) {
        User user = new User(uuid, name);
        users.put(uuid, user);
    }

    public void destroyUser(User user, Player player, boolean delete) {
        if (user.hasBalloon()) user.removeBalloon();
        if (user.hasBanner()) user.removeBanner(player);
        if (delete) users.remove(user.getUuid());
    }

    public void onDisable() {
        for (User user : users.values()) {
            destroyUser(user, user.getPlayer(), false);
        }
    }
}
