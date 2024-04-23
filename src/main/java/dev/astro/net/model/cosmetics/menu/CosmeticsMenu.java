package dev.astro.net.model.cosmetics.menu;

import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.menu.buttons.*;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class CosmeticsMenu extends Menu {

    private final Comet plugin;

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate(plugin.getCosmeticsFile().getString("menu.title"));
    }

    @Override
    public int getSize() {
        return 9 * 5;
    }

    @Override
    public boolean isPlaceholder() {
        return plugin.getCosmeticsFile().getBoolean("menu.placeholder");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(2, 1), new BannerButton(plugin, plugin.getBannerManager(), plugin.getUserManager()));
        buttons.put(getSlot(3, 2), new BalloonButton(plugin, plugin.getBalloonManager(), plugin.getUserManager()));
        buttons.put(getSlot(4, 1), new GadgetsButton(plugin));
        buttons.put(getSlot(5, 2), new MascotButton(plugin, plugin.getMascotManager()));
        buttons.put(getSlot(6, 1), new OutfitButton(plugin, plugin.getOutfitManager()));
        buttons.put(getSlot(4, 3), new ParticlesButton(plugin, plugin.getParticlesManager()));
        buttons.put(getSize() - 5, new RemoveCosmeticButton(plugin));

        return buttons;
    }
}
