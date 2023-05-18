package dev.risas.countach.commands.timer.subcommand;

import dev.risas.countach.Countach;
import dev.risas.countach.model.timer.Timer;
import dev.risas.countach.model.timer.TimerManager;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class TimerListCommand extends BaseCommand {

    private final TimerManager timerManager;

    public TimerListCommand(Countach plugin) {
        this.timerManager = plugin.getTimerManager();
    }

    @Command(name = "timer.list", aliases = {"customt.list", "ctimer.list", "ct.list"}, permission = "countach.command.timer.list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        ChatUtil.sendMessage(sender, "&7&m--------->&r &6&lTimers List &7&m<---------");

        if (timerManager.getTimers().isEmpty()) {
            ChatUtil.sendMessage(sender, "&cThere are no timers.");
        }
        else {
            for (Timer timer : timerManager.getTimers().values()) {
                ChatUtil.sendMessage(sender, " &7▶ &e" + timer.getName() + " Timer");
            }
        }

        ChatUtil.sendMessage(sender, ChatUtil.NORMAL_LINE);
    }
}
