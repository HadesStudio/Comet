package dev.rage.comet.commands.timer.subcommand;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.JavaUtil;
import dev.rage.comet.utilities.command.BaseCommand;
import dev.rage.comet.utilities.command.Command;
import dev.rage.comet.utilities.command.CommandArgs;
import dev.rage.comet.Comet;
import dev.rage.comet.model.timer.Timer;
import dev.rage.comet.model.timer.TimerManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public class TimerStartCommand extends BaseCommand {

    private final Comet plugin;
    private final TimerManager timerManager;

    public TimerStartCommand(Comet plugin) {
        this.plugin = plugin;
        this.timerManager = plugin.getTimerManager();
    }

    @Command(name = "timer.start", aliases = {"customt.start", "ctimer.start", "ct.start"}, permission = "comet.command.timer.start", inGameOnly = false)
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
