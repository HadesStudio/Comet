package dev.astro.net.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import lombok.Getter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class Mongo {

    private final String name;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private boolean connected;

    public Mongo(Comet plugin, String name) {
        this.name = name;
        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);

        try {
            MongoClientURI uri = new MongoClientURI(plugin.getDatabaseFile().getString("mongo-uri." + name));

            if (uri.getDatabase() == null) {
                ChatUtil.logger("&cDatabase name is not set in the URI.");
                return;
            }

            this.mongoClient = new MongoClient(uri);
            this.database = mongoClient.getDatabase(uri.getDatabase());
            this.connected = true;
            ChatUtil.logger("&a[" + CometPlugin.getPlugin().getName() + "] MongoDB successfully connected to " + name + " database.");
        }
        catch (MongoException | IllegalArgumentException | NullPointerException exception) {
            ChatUtil.logger("&c[" + CometPlugin.getPlugin().getName() + "] &cMongoDB failed to connect to " + name + " database.");
        }
    }
}
