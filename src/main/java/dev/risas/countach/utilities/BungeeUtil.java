package dev.risas.countach.utilities;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.risas.countach.Countach;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: Countach
 * Date: 23-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class BungeeUtil {

    public void sendBungeeServer(Player player, String server) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(player.getName());
            out.writeUTF(server);

            player.sendPluginMessage(Countach.getInstance(), "BungeeCord", out.toByteArray());
        }
        catch (Exception exception) {
            ChatUtil.sendMessage(player, "&cAn error occurred while trying to send you to the server.");
        }
    }
}
