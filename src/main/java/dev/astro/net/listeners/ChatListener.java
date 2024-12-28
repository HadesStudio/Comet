package dev.astro.net.listeners;

import dev.astro.net.CometPlugin;
import dev.astro.net.integrations.PlaceholderAPIHook;
import dev.astro.net.utilities.ChatUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String message = player.hasPermission("comet.chat.color") ? ChatUtil.translate(event.getMessage()) : event.getMessage();

        event.setCancelled(true);

        for (Player recipient : event.getRecipients()) {
            if (PlaceholderAPIHook.isEnabled()) {
                recipient.sendMessage(PlaceholderAPI.setPlaceholders(player, getFormat(player, message)));
            } else {
                recipient.sendMessage(getFormat(player, message));
            }
        }
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        if (PlaceholderAPIHook.isEnabled()) {
            console.sendMessage(PlaceholderAPI.setPlaceholders(player, getFormat(player, message)));
        } else {
            console.sendMessage(getFormat(player, message));
        }
    }

    private String getFormat(Player player, String message) {
        return CometPlugin.get().getConfigFile().getString("chat.format")
                .replace("<player-name>", player.getName())
                .replace("<message>", message);
    }
}