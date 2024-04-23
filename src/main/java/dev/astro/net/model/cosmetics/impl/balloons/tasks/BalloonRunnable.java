package dev.astro.net.model.cosmetics.impl.balloons.tasks;

import dev.astro.net.user.User;
import dev.astro.net.user.UserManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BalloonRunnable implements Runnable {
    
    private final UserManager userManager;
    
    @Override
    public void run() {
        for (User user : userManager.getUsers().values()) {
            if (!user.hasBalloonModel()) continue;

            if (!user.getBalloonModel().tick()) {
                user.removeBalloon();
            }
        }
    }
}
