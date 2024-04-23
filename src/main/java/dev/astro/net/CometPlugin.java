package dev.astro.net;

import dev.astro.net.utilities.Comet;
import org.bukkit.plugin.java.JavaPlugin;

public class CometPlugin extends JavaPlugin {

    private static Comet instance;

    @Override
    public void onLoad() {
        instance = new Comet(this);
        instance.onLoad();
    }

    @Override
    public void onEnable() {
        instance.onEnable();
    }

    @Override
    public void onDisable() {
        instance.onDisable();
    }

    public static Comet get() {
        return instance;
    }

    public static CometPlugin getPlugin() {
        return CometPlugin.getPlugin(CometPlugin.class);
    }
}