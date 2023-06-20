package dev.rage.comet.user.save_method.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneOptions;
import dev.rage.comet.Comet;
import dev.rage.comet.database.mongo.Mongo;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.user.save_method.FlatType;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FlatMongoMethod extends FlatType {

    private MongoCollection<Document> collection;

    public FlatMongoMethod(Comet plugin, UserManager userManager, Mongo mongo) {
        super(plugin, userManager);

        if (!mongo.isConnected()) {
            for (int i = 0; i < 3; i++) {
                System.out.println("Error trying register mongo profiles save method, mongo is not connected.");
            }
            return;
        }

        this.collection = mongo.getDatabase().getCollection("profiles");
    }

    @Override
    public CompletableFuture<Void> load(User user) {
        return CompletableFuture.runAsync(() -> {
            Document document = collection.find(new Document("uuid", user.getUuid().toString())).first();

            if (document == null) {
                save(user);
                return;
            }

            if (document.containsKey("balloon")) {
                String balloonName = document.getString("balloon");
                if (!plugin.getBalloonManager().exists(balloonName)) {
                    document.remove("balloon");
                    return;
                }

                user.setBalloon(plugin.getBalloonManager().getBalloonHead(balloonName));
            }

            if (document.containsKey("banner")) {
                String bannerName = document.getString("banner");
                if (!plugin.getBannerManager().exists(bannerName)) {
                    document.remove("banner");
                    return;
                }

                user.setBanner(plugin.getBannerManager().getBanner(bannerName));
            }

            if (document.containsKey("mascot")) {
                String mascotName = document.getString("mascot");
                if (!plugin.getMascotManager().exists(mascotName)) {
                    document.remove("mascot");
                    return;
                }

                user.setMascot(plugin.getMascotManager().getBody(mascotName));
            }

            if (document.containsKey("outfit")) {
                String outfitName = document.getString("outfit");
                if (!plugin.getOutfitManager().exists(outfitName)) {
                    document.remove("outfit");
                    return;
                }

                user.setOutfit(plugin.getOutfitManager().getOutfit(outfitName));
            }

            if (document.containsKey("particle")) {
                String particleName = document.getString("particle");
                if (!plugin.getParticlesManager().exists(particleName)) {
                    document.remove("particle");
                    return;
                }

                user.setParticle(plugin.getParticlesManager().getParticle(particleName));
            }
        });
    }

    @Override
    public CompletableFuture<Void> save(User user) {
        return CompletableFuture.runAsync(() -> {
            Document document = new Document();
            String path = "users." + user.getUuid().toString() + ".";

            if (user.getBalloon() != null) document.put(path + "balloon", user.getBalloon());
            if (user.getBanner() != null) document.put(path + "banner", user.getBanner());
            if (user.getMascot() != null) document.put(path + "mascot", user.getMascot());
            if (user.getOutfit() != null) document.put(path + "outfit", user.getOutfit());
            if (user.getParticle() != null) document.put(path + "particle", user.getParticle());

            collection.insertOne(document, new InsertOneOptions().bypassDocumentValidation(true));
            //collection.insertOne(plugin.getUserManager(), document, new InsertOneOptions().bypassDocumentValidation(true));
        });
    }

    @Override
    public User getUserFromDB(String name) {
        User user = new User(findUUID(name).join(), name);

        load(user);

        return user;
    }

    @Override
    public User getUserFromDB(UUID uuid) {
        User user = new User(uuid, findName(uuid).join());

        load(user);

        return user;
    }

    private CompletableFuture<UUID> findUUID(String name) {
        return CompletableFuture.supplyAsync(() -> collection.find(Filters.eq("name", name)).first().get("uuid", UUID.class));
    }

    private CompletableFuture<String> findName(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> collection.find(Filters.eq("uuid", uuid.toString())).first().getString("name"));
    }
}
