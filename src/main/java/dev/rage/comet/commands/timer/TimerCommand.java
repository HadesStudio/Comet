package dev.rage.comet.commands.timer;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.command.BaseCommand;
import dev.rage.comet.utilities.command.Command;
import dev.rage.comet.utilities.command.CommandArgs;

public class TimerCommand extends BaseCommand {

    @Command(name = "timer", aliases = {"customt", "ctimer", "ct"}, permission = "comet.command.timer", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        String label = command.getLabel();

        ChatUtil.sendMessage(command.getSender(), new String[]{
                ChatUtil.NORMAL_LINE,
                "&6&lTimer Commands",
                "",
                " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                "",
                " &7▶ &e/" + label + " start <timer> <time> <prefix> &7- &fStart a timer.",
                " &7▶ &e/" + label + " stop <timer> &7- &fStop a timer.",
                " &7▶ &e/" + label + " list &7- &fSee all timers.",
                ChatUtil.NORMAL_LINE
        });
    }
}
