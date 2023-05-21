package dev.rage.comet.model.cosmetics.impl.balloons.commands;

import dev.rage.comet.model.cosmetics.impl.balloons.Balloon;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonsManager;
import dev.rage.comet.model.cosmetics.impl.balloons.Heads;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.command.BaseCommand;
import dev.rage.comet.utilities.command.Command;
import dev.rage.comet.utilities.command.CommandArgs;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DevCommand extends BaseCommand {

    private final BalloonsManager balloonsManager;

    @Command(name = "dev", permission = "balloon.dev", aliases = {"balloondev", "balloon.dev"})
    @Override
    public void onCommand(CommandArgs command) {
        new Balloon(command.getPlayer(), Heads.DEV).spawn(balloonsManager);
        command.getPlayer().sendMessage(ChatUtil.translate("&aSpawned balloon."));
    }
}
