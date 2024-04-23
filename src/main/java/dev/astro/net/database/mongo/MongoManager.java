package dev.astro.net.database.mongo;

import dev.astro.net.utilities.Comet;
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

        if (plugin.getConfigFile().getString("save_method").equalsIgnoreCase("MONGO")) {
            this.mongoMap.put("comet", new Mongo(plugin, "comet"));
        }
    }

    public Mongo getMongo(String name) {
        return mongoMap.get(name);
    }
}
