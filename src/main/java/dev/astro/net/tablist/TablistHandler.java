package dev.astro.net.tablist;

import com.github.retrooper.packetevents.PacketEventsAPI;

import dev.astro.net.CometPlugin;
import dev.astro.net.tablist.adapter.impl.CustomTablistProvider;
import dev.astro.net.utilities.ChatUtil;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import dev.astro.net.tablist.adapter.TabAdapter;
import dev.astro.net.tablist.listener.SkinCacheListener;
import dev.astro.net.tablist.listener.TabListener;
import dev.astro.net.tablist.listener.TeamsPacketListener;
import dev.astro.net.tablist.setup.TabLayout;
import dev.astro.net.tablist.skin.SkinCache;
import dev.astro.net.tablist.thread.TablistThread;
import dev.astro.net.tablist.util.PacketUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class TablistHandler {

    /**
     * Static instance of this Tablist Handler
     */
    @Getter private static TablistHandler instance;
    /**
     * This caches each player's {@link TabLayout} as this API is per player.
     *            Player UUID -> TabLayout
     */
    private final Map<UUID, TabLayout> layoutMapping = new ConcurrentHashMap<>();
    /**
     * The plugin registering this Tablist Handler
     */
    private final JavaPlugin plugin;
    /**
     * Our custom Skin Cache that stores every online player's Skin
     */
    private SkinCache skinCache;
    /**
     * Tablist Adapter of this instance
     */
    private TabAdapter adapter;
    /**
     * Main tablist listener
     */
    private TabListener listener;
    /**
     * This thread handles all the operations surrounding
     * ticking and updating the NameTags
     */
    private TablistThread thread;
    private PacketEventsAPI<?> packetEvents;
    private final boolean debug;

    public TablistHandler(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        this.debug = Boolean.getBoolean("BDebug");
    }

    /**
     * Set up the PacketEvents instance of this Tablist Handler.
     * We let the plugin initialize and handle the PacketEvents instance.
     */
    public void init(PacketEventsAPI<?> packetEventsAPI, TeamsPacketListener listener) {
        this.packetEvents = packetEventsAPI;
        this.adapter = new CustomTablistProvider(CometPlugin.get());
        this.listener = new TabListener(this);

        this.packetEvents.getEventManager().registerListener(listener);
        Bukkit.getPluginManager().registerEvents(this.listener, plugin);

        this.setupSkinCache();
    }

    public void setupSkinCache() {
        this.skinCache = new SkinCache();
        Bukkit.getPluginManager().registerEvents(new SkinCacheListener(this), plugin);
    }

    public void registerAdapter(TabAdapter tabAdapter, long ticks) {
        this.adapter = tabAdapter == null ? new CustomTablistProvider(CometPlugin.get()) : tabAdapter;

        if (ticks < 20L)
            ticks = 20L;

        if (Bukkit.getMaxPlayers() < 60) {
            Bukkit.getConsoleSender().sendMessage(ChatUtil.translate("&cMax Players is below 60, this will cause issues for players on 1.7 and below!"));
        }

        this.thread = new TablistThread(this);
        this.thread.runTaskTimerAsynchronously(plugin, 0L, ticks);
    }

    public void unload() {
        if (this.listener != null) {
            HandlerList.unregisterAll(this.listener);
            this.listener = null;
        }

        // Destroy player scoreboards.
        for ( Map.Entry<UUID, TabLayout> entry : this.layoutMapping.entrySet()) {
            UUID uuid = entry.getKey();

            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                continue;
            }

            // Destroy 1.7 teams
            if (PacketUtils.isLegacyClient(player)) {
                for ( int i = 0; i < 60; i++ ) {
                    Team team = player.getScoreboard().getTeam("$" + TabLayout.TAB_NAMES[i]);
                    if (team != null) {
                        team.unregister();
                    }
                }
            }

            // Destroy main tablist team
            Team team = player.getScoreboard().getTeam("tab");
            if (team != null) {
                team.unregister();
            }

            this.layoutMapping.remove(uuid);
        }

        this.thread.cancel();
        this.thread = null;
    }
}