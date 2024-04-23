package dev.astro.net.user.database.impl;

import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.user.User;
import dev.astro.net.user.UserManager;
import dev.astro.net.user.database.Database;
import dev.astro.net.utilities.file.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlatFileDatabase extends Database {

    private final FileConfig fileConfig;
    private final FileConfiguration configuration;

    public FlatFileDatabase(Comet plugin, UserManager userManager) {
        super(plugin, userManager);

        this.fileConfig = new FileConfig(CometPlugin.getPlugin(), "users.yml");
        this.configuration = fileConfig.getConfiguration();
    }

    @Override
    public void load(User user) {
        String path = "users." + user.getUuid().toString() + ".";

        if (configuration.contains(path + "parkourTime")) {
            user.setParkourTime(configuration.getLong(path + "parkourTime"));
        }

        if (configuration.contains(path + "balloon")) {
            String balloonName = configuration.getString(path + "balloon");

            if (!plugin.getBalloonManager().exists(balloonName)) {
                configuration.set(path + "balloon", null);
                return;
            }

            user.setBalloon(plugin.getBalloonManager().getBalloonHead(balloonName));
        }

        if (configuration.contains(path + "banner")) {
            String bannerName = configuration.getString(path + "banner");

            if (!plugin.getBannerManager().exists(bannerName)) {
                configuration.set(path + "banner", null);
                return;
            }

            user.setBanner(plugin.getBannerManager().getBanner(bannerName));
        }

        if (configuration.contains(path + "mascot")) {
            String mascotName = configuration.getString(path + "mascot");

            if (!plugin.getMascotManager().exists(mascotName)) {
                configuration.set(path + "mascot", null);
                return;
            }

            user.setMascot(plugin.getMascotManager().getBody(mascotName));
        }

        if (configuration.contains(path + "outfit")) {
            String outfitName = configuration.getString(path + "outfit");

            if (!plugin.getOutfitManager().exists(outfitName)) {
                configuration.set(path + "outfit", null);
                return;
            }

            user.setOutfit(plugin.getOutfitManager().getOutfit(outfitName));
        }

        if (configuration.contains(path + "particle")) {
            String particleName = configuration.getString(path + "particle");

            if (!plugin.getParticlesManager().exists(particleName)) {
                configuration.set(path + "particle", null);
                return;
            }

            user.setParticle(plugin.getParticlesManager().getParticle(particleName));
        }
    }

    @Override
    public void save(User user) {
        String path = "users." + user.getUuid().toString() + ".";

        configuration.set(path + "name", user.getName());
        configuration.set(path + "parkourTime", user.getParkourTime());
        configuration.set(path + "balloon", user.getBalloon() == null ? null : user.getBalloon().getId());
        configuration.set(path + "banner", user.getBanner() == null ? null : user.getBanner().getId());
        configuration.set(path + "mascot", user.getMascot() == null ? null : user.getMascot().getName());
        configuration.set(path + "outfit", user.getOutfit() == null ? null : user.getOutfit().getName());
        configuration.set(path + "particle", user.getParticle() == null ? null : user.getParticle().getName());

        fileConfig.save();
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

        for (String key : configuration.getConfigurationSection("users").getKeys(false)) {
            users.add(getUserFromDB(UUID.fromString(key)));
        }

        return users;
    }

    private UUID findUUID(String name) {
        String path = "users.";

        for (String key : configuration.getConfigurationSection(path).getKeys(false)) {
            if (configuration.getString(path + key + ".name").equalsIgnoreCase(name)) return UUID.fromString(key);
        }

        return null;
    }

    private String findName(UUID uuid) {
        return configuration.getString("users." + uuid.toString() + ".name");
    }
}
