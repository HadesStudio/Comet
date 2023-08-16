package dev.rage.comet.command.impl.parkour;

import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.utilities.ChatUtil;

public class ParkourCommand extends BaseCommand {

    @Command(name = "parkour", permission = "comet.command.parkour", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        ChatUtil.sendMessage(command.getSender(), new String[]{
                ChatUtil.NORMAL_LINE,
                "&6&lParkour Commands",
                "",
                " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                "",
                " &7▶ &e/parkour startLocation &7- &fSet the start location.",
                " &7▶ &e/parkour endLocation &7- &fSet the end location.",
                " &7▶ &e/parkour checkpoint &7- &fGet the checkpoint location.",
                ChatUtil.NORMAL_LINE
        });
    }
}
