package dev.rage.comet.model.cosmetics.impl.gadgets;

import com.google.common.collect.Lists;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.impl.Mount;

import java.util.List;

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
    }
}
