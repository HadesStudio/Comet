package dev.astro.net.user.database.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneOptions;
import dev.astro.net.utilities.Comet;
import dev.astro.net.database.mongo.Mongo;
import dev.astro.net.user.User;
import dev.astro.net.user.UserManager;
import dev.astro.net.user.database.Database;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MongoDatabase extends Database {

    private MongoCollection<Document> collection;

    public MongoDatabase(Comet plugin, UserManager userManager, Mongo mongo) {
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
    public void load(User user) {
        Document document = collection.find(new Document("uuid", user.getUuid().toString())).first();

        if (document == null) {
            save(user);
            return;
        }

        if (document.containsKey("parkourTime")) {
            user.setParkourTime(document.getLong("parkourTime"));
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
    }

    @Override
    public void save(User user) {
        Document document = new Document();

        document.put("uuid", user.getUuid().toString());
        document.put("name", user.getName());
        document.put("parkourTime", user.getParkourTime());

        if (user.getBalloon() != null) document.put("balloon", user.getBalloon().getId());
        if (user.getBanner() != null) document.put("banner", user.getBanner().getId());
        if (user.getMascot() != null) document.put("mascot", user.getMascot().getName());
        if (user.getOutfit() != null) document.put("outfit", user.getOutfit().getName());
        if (user.getParticle() != null) document.put("particle", user.getParticle().getName());

        collection.insertOne(document, new InsertOneOptions().bypassDocumentValidation(true));
    }

    @Override
    public User getUserFromDB(String name) {
        User user = new User(findUUID(name), name);
        load(user);
        return user;
    }

    @Override
    public User getUserFromDB(UUID uuid) {
        User user = new User(uuid, findName(uuid));
        load(user);
        return user;
    }

    @Override
    public List<User> getUsersFromDB() {
        List<User> users = new ArrayList<>();

        for (Document document : collection.find()) {
            User user = getUserFromDB(document.getString("uuid"));
            users.add(user);
        }

        return users;
    }

    private UUID findUUID(String name) {
        return collection.find(Filters.eq("name", name)).first().get("uuid", UUID.class);
    }

    private String findName(UUID uuid) {
        return collection.find(Filters.eq("uuid", uuid.toString())).first().getString("name");
    }
}
