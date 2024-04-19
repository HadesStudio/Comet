package dev.rage.comet.utilities.actionbar;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import dev.rage.comet.CometPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarAPI {
    private static final short SERVER_VERSION = Short.parseShort(Bukkit.getServer().getBukkitVersion().split("\\.")[1].split("-")[0]);

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return; // Player may have logged out

        // Call the event, if cancelled don't send Action Bar
        ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);

        if (actionBarMessageEvent.isCancelled()) return;

        WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromText(message);

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer actionBarPacket;
        if (SERVER_VERSION >= 19) {
            actionBarPacket = protocolManager.createPacket(PacketType.Play.Server.CHAT);
            StructureModifier<Integer> integers = actionBarPacket.getIntegers();
            if (integers.size() == 1) {
                integers.write(0, (int) EnumWrappers.ChatType.GAME_INFO.getId());
            } else {
                actionBarPacket.getBooleans().write(0, true);
            }
            actionBarPacket.getStrings().write(0, wrappedChatComponent.getJson());
        } else if (SERVER_VERSION > 16 || SERVER_VERSION < 11) {
            actionBarPacket = protocolManager.createPacket(PacketType.Play.Server.CHAT);
            actionBarPacket.getChatTypes().write(0, EnumWrappers.ChatType.GAME_INFO);
            if (actionBarPacket.getBytes().size() == 1) {
                actionBarPacket.getBytes().write(0, (byte) 2);
            }
            actionBarPacket.getChatComponents().write(0, wrappedChatComponent);
        } else {
            actionBarPacket = protocolManager.createPacket(PacketType.Play.Server.TITLE);
            actionBarPacket.getTitleActions().write(0, EnumWrappers.TitleAction.ACTIONBAR);
            actionBarPacket.getChatComponents().write(0, wrappedChatComponent);
        }
        protocolManager.sendServerPacket(player, actionBarPacket);
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
