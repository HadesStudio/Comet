package dev.astro.net.model.hcfcore.vapor;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dev.astro.net.database.mongo.Mongo;
import dev.astro.net.model.hcfcore.IHCFCore;
import org.bson.Document;

import java.util.UUID;

public class VaporKitMap implements IHCFCore {

    private final Mongo mongo;
    private MongoCollection<Document> users;

    public VaporKitMap(Mongo mongo) {
        this.mongo = mongo;

        if (mongo.isConnected()) {
            this.users = mongo.getDatabase().getCollection("userdata");
        }
    }

    @Override
    public int getKills(UUID uuid) {
        Document userData = this.getUserData(uuid);
        return userData == null ? 0 : userData.getInteger("kills");
    }

    @Override
    public int getDeaths(UUID uuid) {
        Document userData = this.getUserData(uuid);
        return userData == null ? 0 : userData.getInteger("deaths");
    }

    public int getKillStreak(UUID uuid) {
        Document userData = this.getUserData(uuid);
        return userData == null ? 0 : userData.getInteger("streak");
    }

    @Override
    public int getLives(UUID uuid) {
        return 0;
    }

    @Override
    public String getDeathban(UUID uuid) {
        return null;
    }

    @Override
    public boolean isConnected() {
        return mongo.isConnected();
    }

    private Document getUserData(UUID uuid) {
        return users.find(Filters.eq("_id", uuid.toString())).first();
    }
}
