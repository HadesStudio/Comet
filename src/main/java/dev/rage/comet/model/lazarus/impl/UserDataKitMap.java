package dev.rage.comet.model.lazarus.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dev.rage.comet.database.mongo.Mongo;
import dev.rage.comet.model.lazarus.ILazarusUserData;
import org.bson.Document;

import java.util.UUID;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

public class UserDataKitMap implements ILazarusUserData {

    private final Mongo mongo;
    private MongoCollection<Document> users;

    public UserDataKitMap(Mongo mongo) {
        this.mongo = mongo;
        if (mongo.isConnected()) {
            this.users = mongo.getDatabase().getCollection("userdata");
        }
    }

    @Override
    public int getKills(UUID uuid) {
        Document document = this.getUserdata(uuid);
        return document == null ? 0 : document.getInteger("kills");
    }

    @Override
    public int getDeaths(UUID uuid) {
        Document document = this.getUserdata(uuid);
        return document == null ? 0 : document.getInteger("deaths");
    }

    public int getKillStreak(UUID uuid) {
        Document document = this.getUserdata(uuid);
        return document == null ? 0 : document.getInteger("killstreak");
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

    private Document getUserdata(UUID uuid) {
        return users.find(Filters.eq("_id", uuid)).first();
    }
}
