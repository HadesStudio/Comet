package dev.risas.countach.commands.timer.subcommand;

import dev.risas.countach.Countach;
import dev.risas.countach.model.timer.Timer;
import dev.risas.countach.model.timer.TimerManager;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class TimerStopCommand extends BaseCommand {

    private final TimerManager timerManager;

    public TimerStopCommand(Countach plugin) {
        this.timerManager = plugin.getTimerManager();
    }

    @Command(name = "timer.stop", aliases = {"customt.stop", "ctimer.stop", "ct.stop"}, permission = "countach.command.timer.stop", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        String label = command.getLabel().replace(".stop", "");

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " stop <timer>");
            return;
        }

        String timerName = args[0];

        if (!timerManager.existsTimer(timerName)) {
            ChatUtil.sendMessage(sender, "&cTimer '" + timerName + "' is not activated.");
            return;
        }

        Timer timer = timerManager.getTimer(timerName);
        timer.setRunning(false);
        ChatUtil.sendMessage(sender, "&cCustom Timer '&f" + timerName + "&c' has been stopped.");
    }
}
