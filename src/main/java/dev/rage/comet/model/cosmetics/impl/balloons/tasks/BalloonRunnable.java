package dev.rage.comet.model.cosmetics.impl.balloons.tasks;

import dev.rage.comet.user.User;
import dev.rage.comet.user.UserManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BalloonRunnable implements Runnable {
    
    private final UserManager userManager;
    
    @Override
    public void run() {
        for (User user : userManager.getUsers().values()) {
            if (!user.hasBalloon()) continue;

            if (!user.getBalloonModel().tick()) {
                user.removeBalloon();
            }
        }
    }
}
