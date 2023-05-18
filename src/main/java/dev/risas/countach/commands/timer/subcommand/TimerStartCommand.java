package dev.risas.countach.commands.timer.subcommand;

import dev.risas.countach.Countach;
import dev.risas.countach.model.timer.Timer;
import dev.risas.countach.model.timer.TimerManager;
import dev.risas.countach.utilities.ChatUtil;
import dev.risas.countach.utilities.JavaUtil;
import dev.risas.countach.utilities.command.BaseCommand;
import dev.risas.countach.utilities.command.Command;
import dev.risas.countach.utilities.command.CommandArgs;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public class TimerStartCommand extends BaseCommand {

    private final Countach plugin;
    private final TimerManager timerManager;

    public TimerStartCommand(Countach plugin) {
        this.plugin = plugin;
        this.timerManager = plugin.getTimerManager();
    }

    @Command(name = "timer.start", aliases = {"customt.start", "ctimer.start", "ct.start"}, permission = "countach.command.timer.start", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        String label = command.getLabel().replace(".start", "");

        if (args.length < 3) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " start <timer> <time> <prefix>");
            return;
        }

        String timerName = args[0];

        if (timerManager.existsTimer(timerName)) {
            ChatUtil.sendMessage(sender, "&cTimer '" + timerName + "' already exist.");
            return;
        }

        long duration = JavaUtil.formatLong(args[1]);

        if (duration == -1L) {
            ChatUtil.sendMessage(sender, "&c" + args[1] + " is an invalid duration.");
            return;
        }

        if (duration < 1000L) {
            ChatUtil.sendMessage(sender, "&cTimer must last for at least 20 ticks.");
            return;
        }

        String prefix = StringUtils.join(args, ' ', 2, args.length);

        Timer timer = new Timer(plugin, timerName, System.currentTimeMillis(), System.currentTimeMillis() + duration, prefix);
        timerManager.createTimer(timer);
        ChatUtil.sendMessage(sender, "&aCustom Timer '&f" + timerName + "&a' has been created.");
    }
}
