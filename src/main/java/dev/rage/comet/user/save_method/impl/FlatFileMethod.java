package dev.rage.comet.user.save_method.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.user.save_method.FlatType;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FlatFileMethod extends FlatType {

    private final FileConfig fileConfig;
    private final FileConfiguration configuration;

    public FlatFileMethod(Comet plugin, UserManager userManager) {
        super(plugin, userManager);

        this.fileConfig = new FileConfig(plugin, "users.yml");
        this.configuration = fileConfig.getConfiguration();
    }

    @Override
    public CompletableFuture<Void> load(User user) {
        return CompletableFuture.runAsync(() -> {
            String path = "users." + user.getUuid().toString() + ".";

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
        });
    }

    @Override
    public CompletableFuture<Void> save(User user) {
        return CompletableFuture.runAsync(() -> {
            String path = "users." + user.getUuid().toString() + ".";

            if (user.getBalloon() != null) configuration.set(path + "balloon", user.getBalloon());
            if (user.getBanner() != null) configuration.set(path + "banner", user.getBanner());
            if (user.getMascot() != null) configuration.set(path + "mascot", user.getMascot());
            if (user.getOutfit() != null) configuration.set(path + "outfit", user.getOutfit());
            if (user.getParticle() != null) configuration.set(path + "particle", user.getParticle());

            fileConfig.save();
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
        User user = new User(uuid, findName(uuid));

        load(user);

        return user;
    }

    private CompletableFuture<UUID> findUUID(String name) {
        return CompletableFuture.supplyAsync(() -> {
            String path = "users.";

            for (String key : configuration.getConfigurationSection(path).getKeys(false)) {
                if (configuration.getString(path + key + ".name").equalsIgnoreCase(name)) return UUID.fromString(key);
            }

            return null;
        });
    }

    private String findName(UUID uuid) {
        return configuration.getString("users." + uuid.toString() + ".name");
    }
}
