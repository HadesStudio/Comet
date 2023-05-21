package dev.rage.comet.model.cosmetics.impl.balloons.tasks;

import dev.rage.comet.model.cosmetics.impl.balloons.Balloon;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonsManager;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class BalloonRunnable implements Runnable {
    
    private final BalloonsManager balloonsManager;
    
    @Override
    public void run() {
        for (Map.Entry<UUID, Balloon> uuidBalloonEntry : balloonsManager.getBalloons().entrySet()) {
            if (uuidBalloonEntry.getValue() == null) {
                balloonsManager.getBalloons().remove(uuidBalloonEntry.getKey());
                continue;
            }

            if (!uuidBalloonEntry.getValue().tick()) {
                uuidBalloonEntry.getValue().remove(balloonsManager);
            }
        }
    }
}
