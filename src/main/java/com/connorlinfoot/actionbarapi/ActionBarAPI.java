package com.connorlinfoot.actionbarapi;

import dev.rage.comet.Comet;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActionBarAPI {

    private static String NMS_VERSION;
    private static boolean PRIORITY_USE_METHODS = false;
    private static boolean USE_OLD_METHODS = false;

    static {
        NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName();
        NMS_VERSION = NMS_VERSION.substring(NMS_VERSION.lastIndexOf(".") + 1);

        if (NMS_VERSION.equalsIgnoreCase("v1_19_R3")) {
            PRIORITY_USE_METHODS = true;
        }
        else if (NMS_VERSION.equalsIgnoreCase("v1_8_R1") || NMS_VERSION.startsWith("v1_7_")) { // Not sure if 1_7 works for the protocol hack?
            USE_OLD_METHODS = true;
        }
    }

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return; // Player may have logged out

        // Call the event, if cancelled don't send Action Bar
        ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);

        if (actionBarMessageEvent.isCancelled()) return;

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            
            if (PRIORITY_USE_METHODS) {
                // Obtén la clase 'CraftPlayer' utilizando Reflection

                // Obtén el objeto 'CraftPlayer' correspondiente al jugador

                // Obtén la clase 'PacketPlayOutChat' utilizando Reflection
                Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutChat");

                // Obtén el constructor de 'PacketPlayOutChat' que toma un objeto 'IChatBaseComponent' como argumento
                Constructor<?> packetConstructor = packetPlayOutChatClass.getConstructor(getNMSClass("IChatBaseComponent"), byte.class);

                // Crea el objeto 'IChatBaseComponent' para el mensaje del ActionBar
                Class<?> chatComponentTextClass = getNMSClass("ChatComponentText");
                Constructor<?> chatComponentTextConstructor = chatComponentTextClass.getConstructor(String.class);
                Object chatComponentText = chatComponentTextConstructor.newInstance(message);

                // Crea la instancia de 'PacketPlayOutChat' utilizando el constructor y el mensaje del ActionBar
                Object packet = packetConstructor.newInstance(chatComponentText, (byte) 2);

                // Obtén el método 'getHandle' de 'CraftPlayer'
                Method getHandleMethod = craftPlayerClass.getMethod("getHandle");

                // Obtén el objeto 'EntityPlayer' a partir de 'CraftPlayer'
                Object entityPlayer = getHandleMethod.invoke(craftPlayer);

                // Obtén el campo 'playerConnection' de 'EntityPlayer'
                Field playerConnectionField = entityPlayer.getClass().getField("playerConnection");

                // Obtén el objeto 'PlayerConnection' a partir del campo
                Object playerConnection = playerConnectionField.get(entityPlayer);

                // Obtén el método 'sendPacket' de 'PlayerConnection' para enviar el paquete al jugador
                Method sendPacketMethod = playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"));

                // Envía el paquete al jugador
                sendPacketMethod.invoke(playerConnection, packet);
            } else {
                Object packet;
                Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".PacketPlayOutChat");
                Class<?> packetClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".Packet");

                if (USE_OLD_METHODS) {
                    Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".ChatSerializer");
                    Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".IChatBaseComponent");
                    Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
                    Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
                } else {
                    Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".ChatComponentText");
                    Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".IChatBaseComponent");
                    try {
                        Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".ChatMessageType");
                        Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                        Object chatMessageType = null;
                        for (Object obj : chatMessageTypes) {
                            if (obj.toString().equals("GAME_INFO")) {
                                chatMessageType = obj;
                            }
                        }
                        Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                        packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                    } catch (ClassNotFoundException cnfe) {
                        Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                        packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                    }
                }
                Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
                Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
                Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
                Object playerConnection = playerConnectionField.get(craftPlayerHandle);
                Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
                sendPacketMethod.invoke(playerConnection, packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String className) throws ClassNotFoundException {
        String fullName = "net.minecraft.server." + NMS_VERSION + "." + className;
        return Class.forName(fullName);
    }

    public static void sendActionBar(Comet plugin, Player player, String message, int duration) {
        sendActionBar(player, message);

        if (duration >= 0) {
            // Sends empty message at the end of the duration. Allows messages shorter than 3 seconds, ensures precision.
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(plugin, duration);
        }

        // Re-sends the messages every 3 seconds so it doesn't go away from the player's screen.
        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(plugin, duration);
        }
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
