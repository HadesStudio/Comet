package dev.rage.comet.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import dev.rage.comet.Comet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class ActionBarAPI {
    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return; // Player may have logged out

        // Call the event, if cancelled don't send Action Bar
        ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);

        if (actionBarMessageEvent.isCancelled()) return;

        PacketContainer actionChat = new PacketContainer(PacketType.Play.Server.CHAT);
        actionChat.getChatTypes().write(0, EnumWrappers.ChatType.GAME_INFO);
        actionChat.getChatComponents().write(0, WrappedChatComponent.fromText(message));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, actionChat);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void sendActionBar(Comet plugin, Player player, String message, int duration) {
        sendActionBar(player, message);

        // Re-sends the messages every 3 seconds, so it doesn't go away from the player's screen.
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks++ > duration) {
                    cancel();
                    return;
                }
                if (ticks % 20 != 0) { // Return if it's between the first 19 ticks. So do it every 1 second.
                    return;
                }
                sendActionBar(player, message);
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

    public static void sendActionBarToAllPlayers(Comet plugin, String message) {
        sendActionBarToAllPlayers(plugin, message, -1);
    }

    public static void sendActionBarToAllPlayers(Comet plugin, String message, int duration) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(plugin, p, message, duration);
        }
    }
}
