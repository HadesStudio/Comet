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

public class UserDataHCF implements ILazarusUserData {

    private final Mongo mongo;
    private MongoCollection<Document> users, deathban;

    public UserDataHCF(Mongo mongo) {
        this.mongo = mongo;

        if (mongo.isConnected()) {
            this.users = mongo.getDatabase().getCollection("userdata");
            this.deathban = mongo.getDatabase().getCollection("deathbans");
        }
    }

    @Override
    public int getKills(UUID uuid) {
        Document document = this.getUserData(uuid);
        return document == null ? 0 : document.getInteger("kills");
    }

    @Override
    public int getDeaths(UUID uuid) {
        Document document = this.getUserData(uuid);
        return document == null ? 0 : document.getInteger("deaths");
    }

    @Override
    public int getKillStreak(UUID uuid) {
        return 0;
    }

    public int getLives(UUID uuid) {
        Document document = this.getUserData(uuid);
        return document == null ? 0 : document.getInteger("lives");
    }

    public String getDeathban(UUID uuid) {
        Document document = this.getDeathbanData(uuid);
        return document == null ? "&aYou don't have deathban." : "&cYou have deathban.";
    }

    @Override
    public boolean isConnected() {
        return mongo.isConnected();
    }

    private Document getUserData(UUID uuid) {
        return users.find(Filters.eq("_id", uuid)).first();
    }

    private Document getDeathbanData(UUID uuid) {
        return deathban.find(Filters.eq("_id", uuid)).first();
    }
}
