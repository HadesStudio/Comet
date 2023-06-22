package dev.rage.comet.model.cosmetics.impl.balloons.listener;

import dev.rage.comet.model.cosmetics.impl.balloons.BalloonManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

@RequiredArgsConstructor
public class BalloonListener implements Listener {

    private final BalloonManager balloonManager;

    @EventHandler
    public void onHit(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();

            if (armorStand.getCustomName() != null) {
                event.setCancelled(true);
            }
        }
    }
}
