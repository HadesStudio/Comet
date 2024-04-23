package dev.astro.net.model.cosmetics.impl.gadgets;

import com.google.common.collect.Lists;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.gadgets.impl.FireTrail;
import dev.astro.net.model.cosmetics.impl.gadgets.impl.GrapplingHook;
import dev.astro.net.model.cosmetics.impl.gadgets.impl.Mount;
import dev.astro.net.model.cosmetics.impl.gadgets.impl.SnowBallHider;
import dev.astro.net.model.cosmetics.impl.gadgets.impl.TeleportBow;
import dev.astro.net.model.cosmetics.impl.gadgets.impl.Trampoline;
import lombok.Getter;

import java.util.List;

@Getter
public class GadgetsManager {

    private final Comet plugin;
    private final List<Gadget> gadgets;

    public GadgetsManager(Comet plugin) {
        this.plugin = plugin;
        this.gadgets = Lists.newArrayList();

        loadOrRefresh(plugin);
    }

    private void loadOrRefresh(Comet plugin) {
        gadgets.add(new Mount(plugin));
        gadgets.add(new TeleportBow(plugin));
        gadgets.add(new SnowBallHider(plugin));
        gadgets.add(new FireTrail(plugin));
        gadgets.add(new GrapplingHook(plugin));
        gadgets.add(new Trampoline(plugin));
    }

    public void onDisable() {
        for (Gadget gadget : gadgets) {
            gadget.onDisable();
        }
    }
}
