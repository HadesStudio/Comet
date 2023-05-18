package dev.risas.countach.database.mongo;

import dev.risas.countach.Countach;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MongoManager {

    private final Countach plugin;
    private final Map<String, Mongo> mongoMap;

    public MongoManager(Countach plugin) {
        this.plugin = plugin;
        this.mongoMap = new HashMap<>();
        this.mongoMap.put("hcf", new Mongo(plugin, "hcf"));
        this.mongoMap.put("kitmap", new Mongo(plugin, "kitmap"));
    }

    public Mongo getMongo(String name) {
        return mongoMap.get(name);
    }

    public void onDisable() {
        mongoMap.values().forEach(Mongo::disconnect);
    }
}
