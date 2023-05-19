package dev.rage.comet.model.cosmetics.impl.banners.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import dev.rage.comet.model.cosmetics.impl.banners.BannersManager;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class BannersMenu extends Menu {

    private final BannersManager bannersManager;

    @Override
    public String getTitle(Player player) {
        return "&6&lBanners";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (Banner banner : bannersManager.getBanners()) {
            buttons.put(buttons.size(), Button.fromItem(banner.getItem(), player1 -> {
                bannersManager.apply(player1, banner);
                player1.sendMessage(ChatUtil.translate("&aYou have applied the " + banner.getName() + " &abanner."));
                player1.closeInventory();
            }));
        }

        return buttons;
    }
}
