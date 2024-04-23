package dev.astro.net.command.impl.timer.subcommand;

import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.timer.Timer;
import dev.astro.net.model.timer.TimerManager;
import dev.astro.net.utilities.file.FileConfig;
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
