package dev.astro.net.command.impl.timer.subcommand;

import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.command.BaseCommand;
import dev.astro.net.command.Command;
import dev.astro.net.command.CommandArgs;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.timer.Timer;
import dev.astro.net.model.timer.TimerManager;
import org.bukkit.command.CommandSender;

public class TimerListCommand extends BaseCommand {

    private final TimerManager timerManager;

    public TimerListCommand(Comet plugin) {
        this.timerManager = plugin.getTimerManager();
    }

    @Command(name = "timer.list", aliases = {"customt.list", "ctimer.list", "ct.list"}, permission = "comet.command.timer.list", inGameOnly = false)
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
