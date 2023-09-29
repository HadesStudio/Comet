package dev.rage.comet.utilities;

import dev.rage.comet.CometPlugin;
import dev.rage.comet.command.CommandManager;
import dev.rage.comet.command.impl.BuildModeCommand;
import dev.rage.comet.command.impl.CometCommand;
import dev.rage.comet.command.impl.parkour.ParkourCommand;
import dev.rage.comet.command.impl.parkour.subcommand.ParkourCheckpointCommand;
import dev.rage.comet.command.impl.parkour.subcommand.ParkourEndLocationCommand;
import dev.rage.comet.command.impl.parkour.subcommand.ParkourStartLocationCommand;
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
import dev.rage.comet.model.cosmetics.impl.balloons.BalloonManager;
import dev.rage.comet.model.cosmetics.impl.banners.BannerManager;
import dev.rage.comet.model.cosmetics.impl.gadgets.GadgetsManager;
import dev.rage.comet.model.cosmetics.impl.mascots.MascotManager;
import dev.rage.comet.model.cosmetics.impl.outfits.OutfitManager;
import dev.rage.comet.model.cosmetics.impl.particles.ParticlesManager;
import dev.rage.comet.model.hcfcore.HCFCoreManager;
import dev.rage.comet.model.hotbar.custom.CustomHotbarManager;
import dev.rage.comet.model.hotbar.normal.NormalHotbarManager;
import dev.rage.comet.model.leaderboard.LeaderboardManager;
import dev.rage.comet.model.parkour.ParkourManager;
import dev.rage.comet.model.parkour.hotbar.ParkourHotbarManager;
import dev.rage.comet.model.queue.QueueManager;
import dev.rage.comet.model.queue.rank.QueueRankManager;
import dev.rage.comet.model.rank.RankManager;
import dev.rage.comet.model.selector.ServerSelectorManager;
import dev.rage.comet.model.server.ServerDataManager;
import dev.rage.comet.model.spawn.SpawnManager;
import dev.rage.comet.model.timer.TimerManager;
import dev.rage.comet.scoreboard.FastBoardListener;
import dev.rage.comet.scoreboard.FastBoardManager;
import dev.rage.comet.scoreboard.FastBoardProvider;
import dev.rage.comet.tablist.Tablist;
import dev.rage.comet.tablist.TablistProvider;
import dev.rage.comet.tablist.head.HeadManager;
import dev.rage.comet.user.UserListener;
import dev.rage.comet.user.UserManager;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.menu.ButtonListener;
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
    private TimerManager timerManager;
    private HeadManager headManager;
    private MongoManager mongoManager;
    private RedisManager redisManager;
    private HCFCoreManager hcfCoreManager;
    private QueueRankManager queueRankManager;
    private QueueManager queueManager;
    private ServerDataManager serverDataManager;
    private BannerManager bannerManager;
    private BalloonManager balloonManager;
    private MascotManager mascotManager;
    private FastBoardManager fastBoardManager;
    private GadgetsManager gadgetsManager;
    private OutfitManager outfitManager;
    private ParticlesManager particlesManager;
    private LeaderboardManager leaderboardManager;
    private Tablist tablist;

    private Plugin plugins;
    private CometPlugin plugin;

    public Comet(CometPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        this.licenseFile = new FileConfig(CometPlugin.getPlugin(), "license.yml");

        this.plugins = new Plugin(this.licenseFile.getString("license-key"), plugin);
    }

    public void onDisable() {
        if (fastBoardManager != null) this.fastBoardManager.onDisable();
        if (tablist != null) this.tablist.onDisable();
        if (userManager != null) this.userManager.onDisable();
        if (redisManager != null) this.redisManager.onDisable();
        if (mascotManager != null) this.mascotManager.onDisable();
    }

    public void onReload() {
        this.configFile.reload();
        this.hotbarFile.reload();
        this.scoreboardFile.reload();
        this.tablistFile.reload();
        this.databaseFile.reload();
        this.queueFile.reload();
        this.serverSelectorFile.reload();
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
        this.headManager.loadOrRefresh();
        this.balloonManager.loadOrRefresh();
        this.outfitManager.loadOrRefresh(this);
        this.mascotManager.loadOrRefresh(this);
        this.bannerManager.loadOrCreate(this);
        if (fastBoardManager != null) this.fastBoardManager.resetBoardAnimation();
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
        this.timerManager = new TimerManager();
        this.headManager = new HeadManager(this);
        this.queueRankManager = new QueueRankManager(this);
        this.queueManager = new QueueManager(this);
        this.hcfCoreManager = new HCFCoreManager(this);
        this.gadgetsManager = new GadgetsManager(this);
        this.outfitManager = new OutfitManager(this);
        this.particlesManager = new ParticlesManager(this);
        this.leaderboardManager = new LeaderboardManager(this);

        if (scoreboardFile.getBoolean("scoreboard.enabled")) {
            this.fastBoardManager = new FastBoardManager(this);
            this.fastBoardManager.setAdapter(new FastBoardProvider(this));
        }
        if (tablistFile.getBoolean("tablist.enabled")) {
            this.tablist = new Tablist(this, new TablistProvider(this));
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

        if (scoreboardFile.getBoolean("scoreboard.enabled")) {
            pluginManager.registerEvents(new FastBoardListener(fastBoardManager), CometPlugin.getPlugin());
        }
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
