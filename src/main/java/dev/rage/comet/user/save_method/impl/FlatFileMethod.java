package dev.rage.comet.user.save_method.impl;

import dev.rage.comet.Comet;
import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.user.save_method.FlatType;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class FlatFileMethod extends FlatType {

    private final FileConfig fileConfig;
    private final FileConfiguration configuration;

    public FlatFileMethod(Comet plugin, UserManager userManager) {
        super(plugin, userManager);

        this.fileConfig = new FileConfig(plugin, "users.yml");
        this.configuration = fileConfig.getConfiguration();
    }

    @Override
    public void load(User user) {
        String path = "users." + user.getUuid().toString() + ".";

        if (configuration.contains(path + "balloon")) {
            String balloonName = configuration.getString(path + "balloon");
            if (!plugin.getBalloonManager().exists(balloonName)) {
                configuration.set(path + "balloon", null);
                return;
            }

            user.setBalloon(plugin.getBalloonManager().getBalloonHead(fileConfig.getString(path + "balloon")));
        }
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User getUser(String name) {
        return null;
    }

    @Override
    public User getUser(UUID uuid) {
        return null;
    }
}
