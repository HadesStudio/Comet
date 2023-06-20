package dev.rage.comet;

import dev.rage.comet.commands.BuildModeCommand;
import dev.rage.comet.commands.CometCommand;
import dev.rage.comet.commands.queue.QueueCommand;
import dev.rage.comet.commands.queue.subcommands.QueueJoinCommand;
import dev.rage.comet.commands.queue.subcommands.QueueLeaveCommand;
import dev.rage.comet.commands.queue.subcommands.QueueListCommand;
import dev.rage.comet.commands.queue.subcommands.QueuePauseCommand;
import dev.rage.comet.commands.spawn.SetSpawnCommand;
import dev.rage.comet.commands.spawn.SpawnCommand;
import dev.rage.comet.commands.timer.TimerCommand;
import dev.rage.comet.commands.timer.subcommand.TimerListCommand;
import dev.rage.comet.commands.timer.subcommand.TimerStartCommand;
import dev.rage.comet.commands.timer.subcommand.TimerStopCommand;
import dev.rage.comet.database.mongo.MongoManager;
import dev.rage.comet.database.redis.RedisManager;
import dev.rage.comet.integrations.PlaceholderAPIHook;
import dev.rage.comet.listeners.PlayerListener;
import dev.rage.comet.listeners.QueueListener;
import dev.rage.comet.listeners.SpawnListener;
import dev.rage.comet.listeners.WorldListener;
import dev.rage.comet.listeners.hotbar.CustomHotbarListener;
import dev.rage.comet.listeners.hotbar.NormalHotbarListener;
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
import dev.rage.comet.model.lunarclient.LunarClientRunnable;
import dev.rage.comet.model.queue.QueueManager;
import dev.rage.comet.model.queue.rank.QueueRankManager;
import dev.rage.comet.model.queue.thread.QueuePositionThread;
import dev.rage.comet.model.queue.thread.QueueReminderThread;
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
import dev.rage.comet.utilities.command.CommandManager;
import dev.rage.comet.utilities.file.FileConfig;
import dev.rage.comet.utilities.menu.ButtonListener;
import dev.rage.comet.utilities.plugin.Plugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Risas
 * Project: Comet
 * Date: 05-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class Comet extends JavaPlugin {

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
            customOutfitsFile;

    private CommandManager commandManager;
    private RankManager rankManager;
    private UserManager userManager;
    private SpawnManager spawnManager;
    private NormalHotbarManager normalHotbarManager;
    private CustomHotbarManager customHotbarManager;
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
    private Tablist tablist;

    @Override
    public void onEnable() {
        this.licenseFile = new FileConfig(this, "license.yml");
        if (!new Plugin(this.licenseFile.getString("license-key"), this).isEnabled()) {
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            Bukkit.getServer().getScheduler().cancelAllTasks();
            System.out.println("[COMET] License key is invalid!");
            return;
        }

        this.configFile = new FileConfig(this, "config.yml");
        this.languageFile = new FileConfig(this, "language.yml");
        this.hotbarFile = new FileConfig(this, "hotbar.yml");
        this.scoreboardFile = new FileConfig(this, "scoreboard.yml");
        this.databaseFile = new FileConfig(this, "database.yml");
        this.queueFile = new FileConfig(this, "queue.yml");
        this.tablistFile = new FileConfig(this, "tablist/tablist.yml");
        this.tablistHeadsFile = new FileConfig(this, "tablist/heads.yml");
        this.serverSelectorFile = new FileConfig(this, "selector/server-selector.yml");
        this.balloonsFile = new FileConfig(this, "cosmetics/balloons.yml");
        this.customOutfitsFile = new FileConfig(this, "cosmetics/custom-outfits.yml");

        this.mongoManager = new MongoManager(this);
        this.redisManager = new RedisManager(this);
        this.commandManager = new CommandManager(this);
        this.rankManager = new RankManager();
        this.mascotManager = new MascotManager(this);
        this.userManager = new UserManager(this, mongoManager.getMongo("comet"));
        this.serverDataManager = new ServerDataManager();
        this.spawnManager = new SpawnManager(this);
        this.bannerManager = new BannerManager();
        this.balloonManager = new BalloonManager(this);
        this.normalHotbarManager = new NormalHotbarManager(this);
        this.customHotbarManager = new CustomHotbarManager(this);
        this.serverSelectorManager = new ServerSelectorManager(this);
        this.timerManager = new TimerManager();
        this.headManager = new HeadManager(this);
        this.queueRankManager = new QueueRankManager(this);
        this.queueManager = new QueueManager(this);
        this.hcfCoreManager = new HCFCoreManager(this);
        this.gadgetsManager = new GadgetsManager(this);
        this.outfitManager = new OutfitManager(this);
        this.particlesManager = new ParticlesManager(this);

        if (scoreboardFile.getBoolean("scoreboard.enabled")) {
            this.fastBoardManager = new FastBoardManager(this);
            this.fastBoardManager.setAdapter(new FastBoardProvider(this));
        }
        if (tablistFile.getBoolean("tablist.enabled")) {
            this.tablist = new Tablist(this, new TablistProvider(this));
        }

        PlaceholderAPIHook.initialize(this);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ButtonListener(), this);
        pluginManager.registerEvents(new PlayerListener(this), this);
        pluginManager.registerEvents(new SpawnListener(this), this);
        pluginManager.registerEvents(new WorldListener(), this);
        pluginManager.registerEvents(new NormalHotbarListener(this), this);
        pluginManager.registerEvents(new CustomHotbarListener(this), this);
        pluginManager.registerEvents(new QueueListener(this), this);
        pluginManager.registerEvents(new UserListener(this), this);

        if (scoreboardFile.getBoolean("scoreboard.enabled")) {
            pluginManager.registerEvents(new FastBoardListener(fastBoardManager), this);
        }

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

        if (configFile.getBoolean("lunar-client.enabled")) {
            Bukkit.getScheduler().runTaskTimer(this, new LunarClientRunnable(this), 0L, 20L);
        }
        if (configFile.getBoolean("action-bar.enabled")) {
            int interval = configFile.getInt("action-bar.interval");
            Bukkit.getScheduler().runTaskTimer(this, new ActionBarRunnable(this, interval), interval * 20L, interval * 20L);
        }

        QueueReminderThread reminderThread = new QueueReminderThread(this);
        reminderThread.start();

        QueuePositionThread positionThread = new QueuePositionThread(this);
        positionThread.start();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new BalloonRunnable(userManager), 0L, 1L);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        if (fastBoardManager != null) this.fastBoardManager.onDisable();
        if (tablist != null) this.tablist.onDisable();
        this.userManager.onDisable();
        this.redisManager.onDisable();
    }

    public void onReload() {
        this.configFile.reload();
        this.hotbarFile.reload();
        this.scoreboardFile.reload();
        this.tablistFile.reload();
        this.databaseFile.reload();
        this.queueFile.reload();
        this.serverSelectorFile.reload();
        this.normalHotbarManager.loadOrRefresh(true);
        this.customHotbarManager.loadOrRefresh(true);
        this.serverSelectorManager.loadOrRefresh();
        this.queueRankManager.loadOrRefresh();
        this.queueManager.loadOrRefresh();
        this.headManager.loadOrRefresh();
        this.balloonManager.loadOrRefresh();
        if (fastBoardManager != null) this.fastBoardManager.resetBoardAnimation();
    }
}
