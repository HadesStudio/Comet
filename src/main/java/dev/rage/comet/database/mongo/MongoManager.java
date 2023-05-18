package dev.rage.comet.database.mongo;

import dev.rage.comet.Comet;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MongoManager {

    private final Comet plugin;
    private final Map<String, Mongo> mongoMap;

    public MongoManager(Comet plugin) {
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
