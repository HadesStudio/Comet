package dev.rage.comet.model.cosmetics.impl.gadgets;

import com.google.common.collect.Lists;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.impl.*;
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
    }
}
