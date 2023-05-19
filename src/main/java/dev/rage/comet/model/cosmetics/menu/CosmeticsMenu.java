package dev.rage.comet.model.cosmetics.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.model.cosmetics.impl.banners.Banner;
import dev.rage.comet.model.cosmetics.impl.banners.BannersManager;
import dev.rage.comet.model.cosmetics.menu.buttons.BannersButton;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class CosmeticsMenu extends Menu {

    private final BannersManager bannersManager;

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9*4;
    }

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate("&6&lCosmetics");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(11, new BannersButton(bannersManager));

        return buttons;
    }
}
