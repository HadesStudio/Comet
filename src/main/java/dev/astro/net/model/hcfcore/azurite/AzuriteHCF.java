package dev.astro.net.model.hcfcore.azurite;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dev.astro.net.database.mongo.Mongo;
import dev.astro.net.model.hcfcore.IHCFCore;
import org.bson.Document;

import java.util.UUID;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class AzuriteHCF implements IHCFCore {

    private final Mongo mongo;
    private MongoCollection<Document> users;

    public AzuriteHCF(Mongo mongo) {
        this.mongo = mongo;

        if (mongo.isConnected()) {
            this.users = mongo.getDatabase().getCollection("users");
        }
    }

    @Override
    public int getKills(UUID uuid) {
        Document userData = this.getUserData(uuid);
        return userData == null ? 0 : Integer.parseInt(userData.getString("kills"));
    }

    @Override
    public int getDeaths(UUID uuid) {
        Document userData = this.getUserData(uuid);
        return userData == null ? 0 : Integer.parseInt(userData.getString("deaths"));
    }

    @Override
    public int getKillStreak(UUID uuid) {
        return 0;
    }

    public int getLives(UUID uuid) {
        Document userData = this.getUserData(uuid);
        return userData == null ? 0 : Integer.parseInt(userData.getString("lives"));
    }

    public String getDeathban(UUID uuid) {
        Document userData = this.getUserData(uuid);
        if (userData == null) return "&aYou don't have deathban.";

        String deathban = userData.getString("deathban");
        return deathban == null ? "&aYou don't have deathban." : "&cYou have deathban.";
    }

    @Override
    public boolean isConnected() {
        return mongo.isConnected();
    }

    private Document getUserData(UUID uuid) {
        return users.find(Filters.eq("_id", uuid.toString())).first();
    }
}
