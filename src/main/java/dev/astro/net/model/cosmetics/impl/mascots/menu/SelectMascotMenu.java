package dev.astro.net.model.cosmetics.impl.mascots.menu;

import com.google.common.collect.Maps;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.mascots.MascotManager;
import dev.astro.net.model.cosmetics.impl.mascots.impl.Body;
import dev.astro.net.user.User;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.menu.Button;
import dev.astro.net.utilities.menu.buttons.PageButton;
import dev.astro.net.utilities.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class SelectMascotMenu extends PaginatedMenu {

    private final Comet plugin;
    private final MascotManager mascotManager;

    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Select a mascot";
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return getTitle(player);
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 27;
    }

    @Override
    public int getSize() {
        return 9*4;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(0, 3), new PageButton(-1, this));
        buttons.put(getSlot(8, 3), new PageButton(1, this));

        buttons.put(getSlot(4, 3), new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.BARRIER)
                        .setName(ChatColor.RED + "Remove Mascot")
                        .build();
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                User user = plugin.getUserManager().getUser(player.getUniqueId());
                if (!user.hasMascotModel()) {
                    player.sendMessage(ChatColor.RED + "You do not have a mascot.");
                    return;
                }
                user.removeMascot();
                player.sendMessage(ChatColor.GREEN + "You have removed your mascot.");
                player.closeInventory();
            }
        });

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (Body body : mascotManager.getSetBodies()) {
            buttons.put(buttons.size(), new MascotButton(body));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private class MascotButton extends Button {

        private final Body body;

        @Override
        public ItemStack getButtonItem(Player player) {
            // Para ahorrarles trabajo
            /*List<String> lore = plugin.getMessagesConfig().getStringList("mascots_lore.locked");

            if (mascotManager.isSpecificUsing(player.getUniqueId(), body.getName())) {
                lore = plugin.getMessagesConfig().getStringList("mascots_lore.used");
            }
            else if (body.isUnlocked(player)) {
                lore = plugin.getMessagesConfig().getStringList("mascots_lore.unused");
            }*/

            return new ItemBuilder(body.getHeadSkull())
                    .setLore(body.getLore())
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!body.isUnlocked(player)) {
                ChatUtil.sendMessage(player, plugin.getLanguageFile().getString("cosmetic-message.no-permission"));
                return;
            }

            User user = plugin.getUserManager().getUser(player.getUniqueId());

            for (String s : plugin.getMascotsFile().getStringList("select-mascot-message")) {
                player.sendMessage(ChatUtil.translate(s.replace("<name>", body.getName())));
            }

            if (user.hasMascotModel())
                user.removeMascot();

            user.applyMascot(plugin, player, body);

            player.closeInventory();
        }
    }
}
