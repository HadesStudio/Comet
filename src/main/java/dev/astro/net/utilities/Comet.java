package dev.astro.net.utilities;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import dev.astro.net.CometPlugin;
import dev.astro.net.command.CommandManager;
import dev.astro.net.command.impl.BuildModeCommand;
import dev.astro.net.command.impl.CometCommand;
import dev.astro.net.command.impl.parkour.ParkourCommand;
import dev.astro.net.command.impl.parkour.subcommand.ParkourCheckpointCommand;
import dev.astro.net.command.impl.parkour.subcommand.ParkourEndLocationCommand;
import dev.astro.net.command.impl.parkour.subcommand.ParkourStartLocationCommand;
import dev.astro.net.command.impl.pvpmode.PvpModeCommand;
import dev.astro.net.command.impl.pvpmode.subcommands.PvpModeJoinCommand;
import dev.astro.net.command.impl.pvpmode.subcommands.PvpModeLeaveCommand;
import dev.astro.net.command.impl.pvpmode.subcommands.PvpModeSetContentCommand;
import dev.astro.net.command.impl.pvpmode.subcommands.PvpModeSetLocationCommand;
import dev.astro.net.command.impl.queue.QueueCommand;
import dev.astro.net.command.impl.queue.subcommands.QueueJoinCommand;
import dev.astro.net.command.impl.queue.subcommands.QueueLeaveCommand;
import dev.astro.net.command.impl.queue.subcommands.QueueListCommand;
import dev.astro.net.command.impl.queue.subcommands.QueuePauseCommand;
import dev.astro.net.command.impl.spawn.SetSpawnCommand;
import dev.astro.net.command.impl.spawn.SpawnCommand;
import dev.astro.net.command.impl.timer.TimerCommand;
import dev.astro.net.command.impl.timer.subcommand.TimerListCommand;
import dev.astro.net.command.impl.timer.subcommand.TimerStartCommand;
import dev.astro.net.command.impl.timer.subcommand.TimerStopCommand;
import dev.astro.net.database.mongo.MongoManager;
import dev.astro.net.database.redis.RedisManager;
import dev.astro.net.integrations.PlaceholderAPIHook;
import dev.astro.net.listeners.PlayerListener;
import dev.astro.net.listeners.QueueListener;
import dev.astro.net.listeners.SpawnListener;
import dev.astro.net.listeners.WorldListener;
import dev.astro.net.listeners.hotbar.CustomHotbarListener;
import dev.astro.net.listeners.hotbar.NormalHotbarListener;
import dev.astro.net.listeners.parkour.ParkourHotbarListener;
import dev.astro.net.listeners.parkour.ParkourListener;
import dev.astro.net.model.cosmetics.impl.balloons.BalloonManager;
import dev.astro.net.model.cosmetics.impl.banners.BannerManager;
import dev.astro.net.model.cosmetics.impl.gadgets.GadgetsManager;
import dev.astro.net.model.cosmetics.impl.mascots.MascotManager;
import dev.astro.net.model.cosmetics.impl.outfits.OutfitManager;
import dev.astro.net.model.cosmetics.impl.particles.ParticlesManager;
import dev.astro.net.model.hcfcore.HCFCoreManager;
import dev.astro.net.model.hotbar.custom.CustomHotbarManager;
import dev.astro.net.model.hotbar.normal.NormalHotbarManager;
import dev.astro.net.model.hubselector.HubSelectorManager;
import dev.astro.net.model.leaderboard.LeaderboardManager;
import dev.astro.net.model.parkour.ParkourManager;
import dev.astro.net.model.parkour.hotbar.ParkourHotbarManager;
import dev.astro.net.model.pvpmode.PvpModeManager;
import dev.astro.net.model.queue.QueueManager;
import dev.astro.net.model.queue.rank.QueueRankManager;
import dev.astro.net.model.rank.RankManager;
import dev.astro.net.model.selector.ServerSelectorManager;
import dev.astro.net.model.server.ServerDataManager;
import dev.astro.net.model.spawn.SpawnManager;
import dev.astro.net.model.timer.TimerManager;
import dev.astro.net.scoreboard.Assemble;
import dev.astro.net.scoreboard.AssembleStyle;
import dev.astro.net.scoreboard.AssembleProvider;
import dev.astro.net.scoreboard.animation.ScoreboardAnimated;
import dev.astro.net.tablist.TablistHandler;
import dev.astro.net.tablist.adapter.impl.CustomTablistProvider;
import dev.astro.net.tablist.listener.TeamsPacketListener;
import dev.astro.net.user.UserListener;
import dev.astro.net.user.UserManager;
import dev.astro.net.utilities.file.FileConfig;
import dev.astro.net.utilities.menu.ButtonListener;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;

/**
 * Created by Risas
 * Project: Comet
 * Date: 05-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter @Setter
public class Comet {

    private FileConfig configFile,
            licenseFile,
            languageFile,
            hotbarFile,
            scoreboardFile,
            tablistFile,
            tablistHeadsFile,
            databaseFile,
            queueFile,
            serverSelectorFile,
            hubSelectorFile,
            pvpModeFile,
            balloonsFile,
            customOutfitsFile,
            cosmeticsFile,
            bannersFile,
            gadgetsFile,
            mascotsFile;

    private CommandManager commandManager;
    private RankManager rankManager;
    private UserManager userManager;
    private SpawnManager spawnManager;
    private ParkourManager parkourManager;
    private NormalHotbarManager normalHotbarManager;
    private CustomHotbarManager customHotbarManager;
    private ParkourHotbarManager parkourHotbarManager;
    private ServerSelectorManager serverSelectorManager;
    private PvpModeManager pvpModeManager;
    private TimerManager timerManager;
    private MongoManager mongoManager;
    private RedisManager redisManager;
    private HCFCoreManager hcfCoreManager;
    private QueueRankManager queueRankManager;
    private QueueManager queueManager;
    private ServerDataManager serverDataManager;
    private BannerManager bannerManager;
    private BalloonManager balloonManager;
    private MascotManager mascotManager;
    private GadgetsManager gadgetsManager;
    private OutfitManager outfitManager;
    private ParticlesManager particlesManager;
    private LeaderboardManager leaderboardManager;
    private HubSelectorManager hubSelectorManager;

    private Plugin plugins;
    private CometPlugin plugin;

    private TablistHandler tablistHandler;
    private PacketEventsAPI<?> packetEventsAPI;

    private Assemble assemble;

    public Comet(CometPlugin plugin) {
        this.plugin = plugin;
    }

    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(CometPlugin.getPlugin()));

        this.packetEventsAPI = PacketEvents.getAPI();
        this.packetEventsAPI.getSettings().bStats(false).checkForUpdates(false);

        this.packetEventsAPI.load();
    }

    public void onEnable() {
        this.licenseFile = new FileConfig(CometPlugin.getPlugin(), "license.yml");

        this.plugins = new Plugin(this.licenseFile.getString("license-key"), plugin);
    }

    public void onDisable() {
        if (tablistHandler != null) this.tablistHandler.unload();
        if (userManager != null) this.userManager.onDisable();
        if (redisManager != null) this.redisManager.onDisable();
        if (mascotManager != null) this.mascotManager.onDisable();
        if (assemble != null) ScoreboardAnimated.disable();
    }

    public void onReload() {
        this.configFile.reload();
        this.hotbarFile.reload();
        this.scoreboardFile.reload();
        this.tablistFile.reload();
        this.databaseFile.reload();
        this.queueFile.reload();
        this.serverSelectorFile.reload();
        this.hubSelectorFile.reload();
        this.pvpModeFile.reload();
        this.balloonsFile.reload();
        this.customOutfitsFile.reload();
        this.cosmeticsFile.reload();
        this.bannersFile.reload();
        this.gadgetsFile.reload();
        this.mascotsFile.reload();
        this.normalHotbarManager.loadOrRefresh(true);
        this.customHotbarManager.loadOrRefresh(true);
        this.serverSelectorManager.loadOrRefresh();
        this.hubSelectorManager.loadOrRefresh();
        this.queueRankManager.loadOrRefresh();
        this.queueManager.loadOrRefresh();
        this.balloonManager.loadOrRefresh();
        this.outfitManager.loadOrRefresh(this);
        this.mascotManager.loadOrRefresh(this);
        this.bannerManager.loadOrCreate(this);
    }

    public void registerManagers() {
        this.configFile = new FileConfig(CometPlugin.getPlugin(), "config.yml");
        this.languageFile = new FileConfig(CometPlugin.getPlugin(), "language.yml");
        this.hotbarFile = new FileConfig(CometPlugin.getPlugin(), "hotbar.yml");
        this.scoreboardFile = new FileConfig(CometPlugin.getPlugin(), "scoreboard.yml");
        this.databaseFile = new FileConfig(CometPlugin.getPlugin(), "database.yml");
        this.queueFile = new FileConfig(CometPlugin.getPlugin(), "queue.yml");
        this.tablistFile = new FileConfig(CometPlugin.getPlugin(), "tablist/tablist.yml");
        this.tablistHeadsFile = new FileConfig(CometPlugin.getPlugin(), "tablist/heads.yml");
        this.serverSelectorFile = new FileConfig(CometPlugin.getPlugin(), "selector/server-selector.yml");
        this.hubSelectorFile = new FileConfig(CometPlugin.getPlugin(), "selector/hub-selector.yml");
        this.pvpModeFile = new FileConfig(CometPlugin.getPlugin(), "pvpmode.yml");
        this.balloonsFile = new FileConfig(CometPlugin.getPlugin(), "cosmetics/balloons.yml");
        this.customOutfitsFile = new FileConfig(CometPlugin.getPlugin(), "cosmetics/custom-outfits.yml");
        this.cosmeticsFile = new FileConfig(CometPlugin.getPlugin(), "cosmetics/cosmetics.yml");
        this.bannersFile = new FileConfig(CometPlugin.getPlugin(), "cosmetics/banners.yml");
        this.gadgetsFile = new FileConfig(CometPlugin.getPlugin(), "cosmetics/gadgets.yml");
        this.mascotsFile = new FileConfig(CometPlugin.getPlugin(), "cosmetics/mascots.yml");

        this.mongoManager = new MongoManager(this);
        this.redisManager = new RedisManager(this);
        this.commandManager = new CommandManager(CometPlugin.getPlugin());
        this.rankManager = new RankManager();
        this.mascotManager = new MascotManager(this);
        this.userManager = new UserManager(this, mongoManager.getMongo("comet"));
        this.serverDataManager = new ServerDataManager();
        this.spawnManager = new SpawnManager(this);
        this.bannerManager = new BannerManager(this);
        this.balloonManager = new BalloonManager(this);
        this.normalHotbarManager = new NormalHotbarManager(this);
        this.customHotbarManager = new CustomHotbarManager(this);
        this.parkourHotbarManager = new ParkourHotbarManager(this);
        this.parkourManager = new ParkourManager(this);
        this.serverSelectorManager = new ServerSelectorManager(this);
        this.hubSelectorManager = new HubSelectorManager(this);
        this.pvpModeManager = new PvpModeManager(this);
        this.timerManager = new TimerManager();
        this.queueRankManager = new QueueRankManager(this);
        this.queueManager = new QueueManager(this);
        this.hcfCoreManager = new HCFCoreManager(this);
        this.gadgetsManager = new GadgetsManager(this);
        this.outfitManager = new OutfitManager(this);
        this.particlesManager = new ParticlesManager(this);
        this.leaderboardManager = new LeaderboardManager(this);

        if (scoreboardFile.getBoolean("scoreboard.enabled")) {
            this.assemble = new Assemble(CometPlugin.getPlugin(), new AssembleProvider(this));
            this.assemble.setAssembleStyle(AssembleStyle.MODERN);
            this.assemble.setTicks(2);

            ScoreboardAnimated.init();
        }
        if (tablistFile.getBoolean("tablist.enabled")) {
            this.packetEventsAPI.init();

            this.tablistHandler = new TablistHandler(CometPlugin.getPlugin());
            this.tablistHandler.init(this.packetEventsAPI, new TeamsPacketListener(this.packetEventsAPI));
            this.tablistHandler.registerAdapter(new CustomTablistProvider(this), 20L);
        }
        PlaceholderAPIHook.initialize(this);
    }

    public void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ButtonListener(), CometPlugin.getPlugin());
        pluginManager.registerEvents(new PlayerListener(this), CometPlugin.getPlugin());
        pluginManager.registerEvents(new SpawnListener(this), CometPlugin.getPlugin());
        pluginManager.registerEvents(new WorldListener(), CometPlugin.getPlugin());
        pluginManager.registerEvents(new NormalHotbarListener(this), CometPlugin.getPlugin());
        pluginManager.registerEvents(new CustomHotbarListener(this), CometPlugin.getPlugin());
        pluginManager.registerEvents(new QueueListener(this), CometPlugin.getPlugin());
        pluginManager.registerEvents(new UserListener(this), CometPlugin.getPlugin());
        pluginManager.registerEvents(new ParkourListener(this), CometPlugin.getPlugin());
        pluginManager.registerEvents(new ParkourHotbarListener(this), CometPlugin.getPlugin());
    }

    public void registerCommands() {
        commandManager.registerCommands(new CometCommand(this));
        commandManager.registerCommands(new BuildModeCommand(this));
        commandManager.registerCommands(new SetSpawnCommand(this));
        commandManager.registerCommands(new SpawnCommand(this));
        commandManager.registerCommands(new TimerCommand());
        commandManager.registerCommands(new TimerStartCommand(this));
        commandManager.registerCommands(new TimerStopCommand(this));
        commandManager.registerCommands(new TimerListCommand(this));
        commandManager.registerCommands(new QueueCommand());
        commandManager.registerCommands(new QueueJoinCommand(this));
        commandManager.registerCommands(new QueueLeaveCommand(this));
        commandManager.registerCommands(new QueuePauseCommand(this));
        commandManager.registerCommands(new QueueListCommand(this));
        commandManager.registerCommands(new ParkourCommand());
        commandManager.registerCommands(new ParkourStartLocationCommand(this));
        commandManager.registerCommands(new ParkourEndLocationCommand(this));
        commandManager.registerCommands(new ParkourCheckpointCommand(this));
        commandManager.registerCommands(new PvpModeCommand());
        commandManager.registerCommands(new PvpModeJoinCommand(pvpModeManager));
        commandManager.registerCommands(new PvpModeLeaveCommand(pvpModeManager));
        commandManager.registerCommands(new PvpModeSetContentCommand(languageFile, pvpModeManager));
        commandManager.registerCommands(new PvpModeSetLocationCommand(languageFile, pvpModeManager));
    }

    public void removeBuggedEntities() {
        for (World world : CometPlugin.getPlugin().getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (mascotManager.getSetBodies().stream().anyMatch(body ->
                        entity.getCustomName() != null && entity.getCustomName().equals(ChatUtil.translate(body.getDisplayName())))) {
                    entity.remove();
                }
            }
        }
    }
}