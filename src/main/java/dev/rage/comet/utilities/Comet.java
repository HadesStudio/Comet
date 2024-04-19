package dev.rage.comet.utilities;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import dev.rage.comet.CometPlugin;
import dev.rage.comet.command.CommandManager;
import dev.rage.comet.command.impl.BuildModeCommand;
import dev.rage.comet.command.impl.CometCommand;
import dev.rage.comet.command.impl.parkour.ParkourCommand;
import dev.rage.comet.command.impl.parkour.subcommand.ParkourCheckpointCommand;
import dev.rage.comet.command.impl.parkour.subcommand.ParkourEndLocationCommand;
import dev.rage.comet.command.impl.parkour.subcommand.ParkourStartLocationCommand;
import dev.rage.comet.command.impl.pvpmode.PvpModeCommand;
import dev.rage.comet.command.impl.pvpmode.subcommands.PvpModeJoinCommand;
import dev.rage.comet.command.impl.pvpmode.subcommands.PvpModeLeaveCommand;
import dev.rage.comet.command.impl.pvpmode.subcommands.PvpModeSetContentCommand;
import dev.rage.comet.command.impl.pvpmode.subcommands.PvpModeSetLocationCommand;
import dev.rage.comet.command.impl.queue.QueueCommand;
import dev.rage.comet.command.impl.queue.subcommands.QueueJoinCommand;
import dev.rage.comet.command.impl.queue.subcommands.QueueLeaveCommand;
import dev.rage.comet.command.impl.queue.subcommands.QueueListCommand;
import dev.rage.comet.command.impl.queue.subcommands.QueuePauseCommand;
import dev.rage.comet.command.impl.spawn.SetSpawnCommand;
import dev.rage.comet.command.impl.spawn.SpawnCommand;
import dev.rage.comet.command.impl.timer.TimerCommand;
import dev.rage.comet.command.impl.timer.subcommand.TimerListCommand;
import dev.rage.comet.command.impl.timer.subcommand.TimerStartCommand;
import dev.rage.comet.command.impl.timer.subcommand.TimerStopCommand;
import dev.rage.comet.database.mongo.MongoManager;
import dev.rage.comet.database.redis.RedisManager;
import dev.rage.comet.integrations.PlaceholderAPIHook;
import dev.rage.comet.listeners.PlayerListener;
import dev.rage.comet.listeners.QueueListener;
import dev.rage.comet.listeners.SpawnListener;
import dev.rage.comet.listeners.WorldListener;
import dev.rage.comet.listeners.hotbar.CustomHotbarListener;
import dev.rage.comet.listeners.hotbar.NormalHotbarListener;
import dev.rage.comet.listeners.parkour.ParkourHotbarListener;
import dev.rage.comet.listeners.parkour.ParkourListener;
import dev.rage.comet.model.actionbar.ActionBarRunnable;
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonManager;
import dev.rage.comet.model.cosmetics.impl.balloons.tasks.BalloonRunnable;
import dev.rage.comet.model.cosmetics.impl.banners.BannerManager;
import dev.rage.comet.model.cosmetics.impl.gadgets.GadgetsManager;
import dev.rage.comet.model.cosmetics.impl.mascots.MascotManager;
import dev.rage.comet.model.cosmetics.impl.outfits.OutfitManager;
import dev.rage.comet.model.cosmetics.impl.particles.ParticlesManager;
import dev.rage.comet.model.hcfcore.HCFCoreManager;
import dev.rage.comet.model.hotbar.custom.CustomHotbarManager;
import dev.rage.comet.model.hotbar.normal.NormalHotbarManager;
import dev.rage.comet.model.leaderboard.LeaderboardManager;
import dev.rage.comet.model.lunarclient.LunarClientRunnable;
import dev.rage.comet.model.parkour.ParkourManager;
import dev.rage.comet.model.parkour.hotbar.ParkourHotbarManager;
import dev.rage.comet.model.pvpmode.PvpModeManager;
import dev.rage.comet.model.queue.QueueManager;
import dev.rage.comet.model.queue.rank.QueueRankManager;
import dev.rage.comet.model.queue.thread.QueuePositionThread;
import dev.rage.comet.model.queue.thread.QueueReminderThread;
import dev.rage.comet.model.rank.RankManager;
import dev.rage.comet.model.selector.ServerSelectorManager;
import dev.rage.comet.model.server.ServerDataManager;
import dev.rage.comet.model.spawn.SpawnManager;
import dev.rage.comet.model.timer.TimerManager;
import dev.rage.comet.scoreboard.Assemble;
import dev.rage.comet.scoreboard.AssembleStyle;
import dev.rage.comet.scoreboard.AssembleProvider;
import dev.rage.comet.scoreboard.animation.ScoreboardAnimated;
import dev.rage.comet.tablist.TablistHandler;
import dev.rage.comet.tablist.adapter.impl.CustomTablistProvider;
import dev.rage.comet.tablist.listener.TeamsPacketListener;
import dev.rage.comet.user.UserListener;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.menu.ButtonListener;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

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

        //this.plugins = new Plugin(this.licenseFile.getString("license-key"), plugin);

        Arrays.asList("      &f&l* &b&lComet &7(&fHubCore&7) &f&l*       ",
                        "",
                        " &f&l* &bAuthors&7: &f" + CometPlugin.getPlugin().getDescription().getAuthors(),
                        " &f&l* &bPlugin Version&7: &f" + CometPlugin.getPlugin().getDescription().getVersion(),
                        " &f&l* &bLicense Status&7: &aVALID",
                        " &f&l* &bThanks for using Comet Hubcore <3",
                        " &f&l* &bIf u have support join to &nastro-operations.com/discord")
                .forEach(s ->
                        Bukkit.getConsoleSender().sendMessage(ChatUtil.translate(s)));

        CometPlugin.get().registerManagers();
        CometPlugin.get().registerListeners();
        CometPlugin.get().registerCommands();

        if (CometPlugin.get().getConfigFile().getBoolean("lunar-client.enabled")) {
            Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new LunarClientRunnable(CometPlugin.get()), 0L, 20L);
        }
        if (CometPlugin.get().getConfigFile().getBoolean("action-bar.enabled")) {
            int interval = CometPlugin.get().getConfigFile().getInt("action-bar.interval");
            Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new ActionBarRunnable(CometPlugin.get(), interval), interval * 20L, interval * 20L);
        }

        QueueReminderThread reminderThread = new QueueReminderThread(CometPlugin.get());
        reminderThread.start();

        QueuePositionThread positionThread = new QueuePositionThread(CometPlugin.get());
        positionThread.start();

        CometPlugin.get().removeBuggedEntities();

        Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new BalloonRunnable(CometPlugin.get().getUserManager()), 0L, 1L);
        Bukkit.getMessenger().registerOutgoingPluginChannel(CometPlugin.getPlugin(), "BungeeCord");
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

            /*
            tablistHandler = new TablistHandler(CometPlugin.getPlugin());

            tablistHandler.init(packetEventsAPI, new TeamsPacketListener(packetEventsAPI));

            if (tablistFile.getString("tablist.type").equalsIgnoreCase("PLAYERS_DISPLAY")) {
                tablistProvider = new PlayersDisplayTablistProvider(this);
            } else {
                tablistProvider = new CustomTablistProvider(this);
            }

            tablistHandler.registerAdapter(tablistProvider, 20L);

             */
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
