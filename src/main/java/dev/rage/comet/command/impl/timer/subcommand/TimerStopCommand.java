package dev.rage.comet.command.impl.timer.subcommand;

import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.command.BaseCommand;
import dev.rage.comet.command.Command;
import dev.rage.comet.command.CommandArgs;
import dev.rage.comet.utilities.Comet;
import dev.rage.comet.model.timer.Timer;
import dev.rage.comet.model.timer.TimerManager;
import dev.rage.comet.utilities.file.FileConfig;
import org.bukkit.command.CommandSender;

public class TimerStopCommand extends BaseCommand {

    private final FileConfig languageFile;
    private final TimerManager timerManager;

    public TimerStopCommand(Comet plugin) {
        this.languageFile = plugin.getLanguageFile();
        this.timerManager = plugin.getTimerManager();
    }

    @Command(name = "timer.stop", aliases = {"customt.stop", "ctimer.stop", "ct.stop"}, permission = "comet.command.timer.stop", inGameOnly = false)
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
            ChatUtil.sendMessage(sender, languageFile.getString("timer-message.not-active")
                    .replace("<timer>", timerName));
            return;
        }

        Timer timer = timerManager.getTimer(timerName);
        timer.setRunning(false);
        ChatUtil.sendMessage(sender, languageFile.getString("timer-message.stopped")
                .replace("<timer>", timerName));
    }
}
