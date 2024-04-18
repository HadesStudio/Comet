package dev.rage.comet.command.impl.pvpmode;

import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.utilities.ChatUtil;

public class PvpModeCommand extends BaseCommand {

    @Command(name = "pvpmode", permission = "comet.command.pvpmode")
    @Override
    public void onCommand(CommandArgs command) {
        ChatUtil.sendMessage(command.getSender(), new String[]{
                ChatUtil.NORMAL_LINE,
                "&6&lPvpMode Commands",
                "",
                " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                "",
                " &7▶ &e/pvpmode join &7- &fJoin pvpmode.",
                " &7▶ &e/pvpmode leave &7- &fLeave pvpmode.",
                " &7▶ &e/pvpmode setcontent &7- &fSet pvpmode content.",
                " &7▶ &e/pvpmode setlocation &7- &fSet pvpmode location.",
                ChatUtil.NORMAL_LINE
        });
    }
}
