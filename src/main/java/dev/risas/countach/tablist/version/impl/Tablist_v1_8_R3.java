package dev.risas.countach.tablist.version.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.google.common.collect.Lists;
import com.mojang.authlib.properties.Property;
import dev.risas.countach.Countach;
import dev.risas.countach.tablist.TablistColumn;
import dev.risas.countach.tablist.TablistEntry;
import dev.risas.countach.tablist.head.Head;
import dev.risas.countach.tablist.head.HeadManager;
import dev.risas.countach.tablist.player.PlayerTablist;
import dev.risas.countach.tablist.player.PlayerVersionManager;
import dev.risas.countach.tablist.player.module.PlayerVersion;
import dev.risas.countach.tablist.utilities.ClientUtil;
import dev.risas.countach.tablist.utilities.TablistUtil;
import dev.risas.countach.tablist.version.TablistVersion;
import dev.risas.countach.utilities.ChatUtil;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Tablist_v1_8_R3 implements TablistVersion {

    private final HeadManager headManager;

    public Tablist_v1_8_R3(Countach plugin) {
        this.headManager = plugin.getHeadManager();
    }

    @Override
    public TablistEntry createEntry(PlayerTablist playerTablist, String string, TablistColumn column, Integer slot, Integer rawSlot) {
        OfflinePlayer offlinePlayer = new OfflinePlayer() {
            private final UUID uuid = UUID.randomUUID();

            public boolean isOnline() {
                return true;
            }

            public String getName() {
                return string;
            }

            public UUID getUniqueId() {
                return this.uuid;
            }

            public boolean isBanned() {
                return false;
            }

            public void setBanned(boolean b) {
            }

            public boolean isWhitelisted() {
                return false;
            }

            public void setWhitelisted(boolean b) {
            }

            public Player getPlayer() {
                return null;
            }

            public long getFirstPlayed() {
                return 0L;
            }

            public long getLastPlayed() {
                return 0L;
            }

            public long getLastLogin() {
                return 0;
            }

            public long getLastLogout() {
                return 0;
            }


            public boolean hasPlayedBefore() {
                return false;
            }

            public Location getBedSpawnLocation() {
                return null;
            }

            public Map<String, Object> serialize() {
                return null;
            }

            public boolean isOp() {
                return false;
            }

            public void setOp(boolean b) {
            }
        };
        
        Player player = playerTablist.getPlayer();
        PlayerVersion playerVersion = PlayerVersionManager.getPlayerVersion(player);
        
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        WrappedGameProfile profile = new WrappedGameProfile(offlinePlayer.getUniqueId(), (playerVersion != PlayerVersion.v1_7) ? string : (ClientUtil.ENTRY.get(rawSlot - 1) + ""));
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, 1, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText((playerVersion != PlayerVersion.v1_7) ? "" : profile.getName()));

        Head defaultHead = headManager.getDefault();

        if (playerVersion != PlayerVersion.v1_7) {
            playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", defaultHead.getValue(), defaultHead.getSignature()));
        }

        packet.getPlayerInfoDataLists().write(0, Lists.newArrayList(playerInfoData));
        sendPacket(player, packet);
        return new TablistEntry(string, offlinePlayer, "", playerTablist, defaultHead, column, slot, rawSlot, 0);
    }

    @Override
    public void updateText(PlayerTablist playerTablist, TablistEntry tabEntry, String text) {
        if (tabEntry.getText().equals(text)) return;
        
        Player player = playerTablist.getPlayer();
        PlayerVersion playerVersion = PlayerVersionManager.getPlayerVersion(player);
        String[] newStrings = PlayerTablist.splitText(text, tabEntry.getRawSlot());
        
        if (playerVersion == PlayerVersion.v1_7) {
            Team team = PlayerTablist.getOrCreateTeam(player.getScoreboard(), ClientUtil.NAMES.get(tabEntry.getRawSlot() - 1));

            team.setPrefix(ChatUtil.translate(newStrings[0]));
            team.setSuffix(ChatUtil.translate(newStrings[1]));
        }
        else {
            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);
            WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getOfflinePlayer().getUniqueId(), tabEntry.getId());
            PlayerInfoData playerInfoData = new PlayerInfoData(profile, tabEntry.getLatency(), EnumWrappers.NativeGameMode.NOT_SET,
                    WrappedChatComponent.fromText(ChatUtil.translate(text)));
            packet.getPlayerInfoDataLists().write(0, Lists.newArrayList(playerInfoData));
            sendPacket(player, packet);
        }
        tabEntry.setText(text);
    }

    @Override
    public void updateLatency(PlayerTablist playerTablist, TablistEntry tabEntry, Integer latency) {
        if (tabEntry.getLatency() == latency) return;
        
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_LATENCY);
        WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getOfflinePlayer().getUniqueId(), tabEntry.getId());
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, latency, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(ChatUtil.translate(tabEntry.getText())));
        packet.getPlayerInfoDataLists().write(0, Lists.newArrayList(playerInfoData));
        
        sendPacket(playerTablist.getPlayer(), packet);
        
        tabEntry.setLatency(latency);
    }

    @Override
    public void updateSkin(PlayerTablist playerTablist, TablistEntry tabEntry, Head head) {
        if (head == null || tabEntry.getHead().equals(head)) return;
        
        Player player = playerTablist.getPlayer();
        WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getOfflinePlayer().getUniqueId(), tabEntry.getId());
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, tabEntry.getLatency(), EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(ChatUtil.translate(tabEntry.getText())));

        playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", head.getValue(), head.getSignature()));

        PacketContainer remove = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        remove.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        remove.getPlayerInfoDataLists().write(0, Lists.newArrayList(playerInfoData));
        PacketContainer add = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        add.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        add.getPlayerInfoDataLists().write(0, Lists.newArrayList(playerInfoData));
        
        sendPacket(player, remove);
        sendPacket(player, add);
        
        tabEntry.setHead(head);
    }
    
    @Override
    public void updateHeaderAndFooter(Player player, List<String> header, List<String> footer) {
        PacketContainer headerAndFooter = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        headerAndFooter.getChatComponents().write(0, WrappedChatComponent.fromText(TablistUtil.getListFromString(ChatUtil.translate(header))));
        headerAndFooter.getChatComponents().write(1, WrappedChatComponent.fromText(TablistUtil.getListFromString(ChatUtil.translate(footer))));
        sendPacket(player, headerAndFooter);

    }

    @Override
    public Head getHead(Player player) {
        if (headManager.exists(player.getUniqueId().toString())) return headManager.getHead(player.getUniqueId().toString());

        try {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            Property property = entityPlayer.getProfile().getProperties().get("textures").stream().findFirst().orElse(null);

            if (property != null) {
                Head head = new Head();
                head.setName(player.getUniqueId().toString());
                head.setValue(property.getValue());
                head.setSignature(property.getSignature());

                return headManager.getHeads().put(player.getUniqueId().toString(), head);
            }
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static void sendPacket(Player player, PacketContainer packetContainer) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
