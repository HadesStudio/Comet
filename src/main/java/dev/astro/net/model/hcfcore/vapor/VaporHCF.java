package dev.astro.net.model.hcfcore.vapor;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dev.astro.net.database.mongo.Mongo;
import dev.astro.net.model.hcfcore.IHCFCore;
import org.bson.Document;

import java.util.UUID;

public class VaporHCF implements IHCFCore {

    private final Mongo mongo;
    private MongoCollection<Document> users;

    public VaporHCF(Mongo mongo) {
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

    @Override
    public int getKillStreak(UUID uuid) {
        return 0;
    }

    public int getLives(UUID uuid) {
        Document userData = this.getUserData(uuid);
        return userData == null ? 0 : userData.getInteger("lives");
    }

    public String getDeathban(UUID uuid) {
        Document deathbanData = this.getDeathbanData(uuid);
        return deathbanData == null ? "&aYou don't have deathban." : "&cYou have deathban.";
    }

    @Override
    public boolean isConnected() {
        return mongo.isConnected();
    }

    private Document getUserData(UUID uuid) {
        return users.find(Filters.eq("_id", uuid)).first();
    }

    private Document getDeathbanData(UUID uuid) {
        return users.find(Filters.eq("_id", uuid)).first();
    }
}
