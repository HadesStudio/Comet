package dev.rage.comet.user.impl;

import dev.rage.comet.model.cosmetics.impl.banners.BannerManager;
import dev.rage.comet.user.IUser;
import dev.rage.comet.user.User;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.configuration.ConfigurationSection;

public class UserFlatFile implements IUser {

    private final BannerManager bannerManager;

    public UserFlatFile(BannerManager bannerManager) {
        this.bannerManager = bannerManager;
    }

    @Override
    public void saveUser(User user) {
        toSavable(user);
    }

    @Override
    public void loadUser(User user, boolean byName) {
        FileConfig userDataFile = user.getFileConfig();
        ConfigurationSection section = userDataFile.getConfiguration();

        if (section == null) {
            this.saveUser(user);
            return;
        }

        this.loadUser(user, section);
    }

    public void loadUser(User user, ConfigurationSection section) {
        user.setBanner(bannerManager.getBanner(section.getString("banner")));
    }

    public void toSavable(User user) {
        FileConfig userDataFile = user.getFileConfig();
        ConfigurationSection section = userDataFile.getConfiguration();

        section.set("banner", user.getBannerId());

        userDataFile.save();
    }
}
