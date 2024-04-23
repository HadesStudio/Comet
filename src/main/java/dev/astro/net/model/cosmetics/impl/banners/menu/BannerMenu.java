package dev.astro.net.model.cosmetics.impl.banners.menu;

import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.banners.Banner;
import dev.astro.net.model.cosmetics.impl.banners.BannerManager;
import dev.astro.net.model.cosmetics.impl.banners.menu.button.BannerButton;
import dev.astro.net.model.cosmetics.impl.banners.menu.button.RemoveBannerButton;
import dev.astro.net.user.UserManager;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class BannerMenu extends Menu {

    private final Comet plugin;
    private final BannerManager bannerManager;
    private final UserManager userManager;

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate("Banners");
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (Banner banner : bannerManager.getBanners().values()) {
            buttons.put(buttons.size(), new BannerButton(plugin, banner, userManager));
        }

        buttons.put(getSize() - 5, new RemoveBannerButton(plugin, userManager));

        return buttons;
    }
}
