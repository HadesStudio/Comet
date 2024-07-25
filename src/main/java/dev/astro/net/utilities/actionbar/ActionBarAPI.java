package dev.astro.net.utilities.actionbar;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarAPI {
    private static final int SERVER_VERSION = Integer.parseInt(
            Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1)
                    .replace("1_", "")
                    .replaceAll("_R\\d", ""));


    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return; // Player may have logged out

        // Call the event, if cancelled don't send Action Bar
        ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);

        if (actionBarMessageEvent.isCancelled()) return;

        if (SERVER_VERSION >= 19) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtil.translate(message)));
        } else if (SERVER_VERSION > 16 || SERVER_VERSION < 11) {
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
            PacketContainer actionBarPacket;
            WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromText(message);

            actionBarPacket = protocolManager.createPacket(PacketType.Play.Server.CHAT);
            actionBarPacket.getChatTypes().write(0, EnumWrappers.ChatType.GAME_INFO);
            if (actionBarPacket.getBytes().size() == 1) {
                actionBarPacket.getBytes().write(0, (byte) 2);
            }
            actionBarPacket.getChatComponents().write(0, wrappedChatComponent);

            protocolManager.sendServerPacket(player, actionBarPacket);
        } else {
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
            PacketContainer actionBarPacket;
            WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromText(message);

            actionBarPacket = protocolManager.createPacket(PacketType.Play.Server.TITLE);
            actionBarPacket.getTitleActions().write(0, EnumWrappers.TitleAction.ACTIONBAR);
            actionBarPacket.getChatComponents().write(0, wrappedChatComponent);

            protocolManager.sendServerPacket(player, actionBarPacket);
        }
    }

    public static void sendActionBar(Player player, String message, int duration) {
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
        }.runTaskTimer(CometPlugin.getPlugin(), 1L, 1L);
    }

    public static void sendActionBarToAllPlayers(String message) {
        sendActionBarToAllPlayers(message, -1);
    }

    public static void sendActionBarToAllPlayers(String message, int duration) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, message, duration);
        }
    }
}