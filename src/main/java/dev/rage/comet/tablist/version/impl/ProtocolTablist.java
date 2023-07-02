package dev.rage.comet.tablist.version.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.collect.Lists;
import dev.rage.comet.Comet;
import dev.rage.comet.tablist.TablistColumn;
import dev.rage.comet.tablist.TablistEntry;
import dev.rage.comet.tablist.head.Head;
import dev.rage.comet.tablist.head.HeadManager;
import dev.rage.comet.tablist.player.PlayerTablist;
import dev.rage.comet.tablist.player.PlayerVersionManager;
import dev.rage.comet.tablist.player.module.PlayerVersion;
import dev.rage.comet.tablist.utilities.ClientUtil;
import dev.rage.comet.tablist.utilities.TablistUtil;
import dev.rage.comet.tablist.version.TablistVersion;
import dev.rage.comet.utilities.ChatUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@Getter
public class ProtocolTablist implements TablistVersion {
    private final HeadManager headManager;

    public ProtocolTablist(Comet comet) {
        this.headManager = comet.getHeadManager();
        ProtocolLibrary.getProtocolManager().addPacketListener(new ProtocolPacketAdapter(comet, this));
    }

    @Override
    public TablistEntry createEntry(PlayerTablist playerTablist, String string, TablistColumn column, Integer slot, Integer rawSlot) {
        UUID uuid = UUID.randomUUID();

        Player player = playerTablist.getPlayer();
        PlayerVersion playerVersion = PlayerVersionManager.getPlayerVersion(player);

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        WrappedGameProfile profile = new WrappedGameProfile(uuid, (playerVersion != PlayerVersion.v1_7) ? string : (ClientUtil.ENTRY.get(rawSlot - 1)));
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, 1, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText((playerVersion != PlayerVersion.v1_7) ? "" : profile.getName()));

        Head defaultHead = headManager.getDefault();

        if (playerVersion != PlayerVersion.v1_7) {
            playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", defaultHead.getValue(), defaultHead.getSignature()));
        }

        packet.getPlayerInfoDataLists().write(0, Lists.newArrayList(playerInfoData));
        sendPacket(player, packet);
        return new TablistEntry(string, uuid, "", playerTablist, defaultHead, column, slot, rawSlot, 0);
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
        } else {
            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);
            WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getUuid(), tabEntry.getId());
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
        WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getUuid(), tabEntry.getId());
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, latency, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(ChatUtil.translate(tabEntry.getText())));
        packet.getPlayerInfoDataLists().write(0, Lists.newArrayList(playerInfoData));

        sendPacket(playerTablist.getPlayer(), packet);

        tabEntry.setLatency(latency);
    }

    @Override
    public void updateSkin(PlayerTablist playerTablist, TablistEntry tabEntry, Head head) {
        if (head == null || tabEntry.getHead().equals(head)) return;

        Player player = playerTablist.getPlayer();
        WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getUuid(), tabEntry.getId());
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
        return this.headManager.getHead(player.getUniqueId().toString());
    }

    private static void sendPacket(Player player, PacketContainer packetContainer) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}