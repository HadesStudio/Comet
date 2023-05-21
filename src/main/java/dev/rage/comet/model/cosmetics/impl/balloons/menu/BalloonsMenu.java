package dev.rage.comet.model.cosmetics.impl.balloons.menu;

import com.google.common.collect.Maps;
import dev.rage.comet.model.cosmetics.impl.balloons.Balloon;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonsManager;
import dev.rage.comet.model.cosmetics.impl.balloons.Heads;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.menu.Button;
import dev.rage.comet.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class BalloonsMenu extends Menu {

    private final BalloonsManager balloonsManager;

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate("&6&lBalloons");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (Heads head : balloonsManager.getHeads()) {
            buttons.put(getSlot(4, 1), new HeadButton(head));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private class HeadButton extends Button {

        private final Heads head;

        @Override
        public ItemStack getButtonItem(Player player) {
            return head.getHeadSkull();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new Balloon(player, head).spawn(balloonsManager);
            player.sendMessage(ChatUtil.translate("&aSpawned balloon."));
        }
    }
}
