package dev.rage.comet.tablist.version.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import dev.rage.comet.CometPlugin;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.tablist.head.Head;

import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * ask first, and give me the credits.
 * Arigato! n.n
 */
public class ProtocolPacketAdapter extends PacketAdapter {
    private final ProtocolTablist protocolTablist;

    public ProtocolPacketAdapter(ProtocolTablist protocolTablist) {
        super(CometPlugin.getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO);
        this.protocolTablist = protocolTablist;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.PLAYER_INFO) {
            return;
        }
        UUID uuid = event.getPlayer().getUniqueId();
        PacketContainer packetContainer = event.getPacket();
        EnumWrappers.PlayerInfoAction action = packetContainer.getPlayerInfoAction().read(0);

        if (action == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
            packetContainer.getPlayerInfoDataLists().read(0).forEach(playerInfoData -> {
                WrappedGameProfile profile = playerInfoData.getProfile();
                WrappedSignedProperty textures = profile.getProperties().get("textures").stream().findFirst().orElse(null);
                if (textures == null) {
                    return;
                }
                String stringUuid = uuid.toString();
                Head head = new Head();
                head.setName(stringUuid);
                head.setValue(textures.getValue());
                head.setSignature(textures.getSignature());
                this.protocolTablist.getHeadManager().getHeads().put(stringUuid, head);
            });
        }
    }
}
