package dev.astro.net.model.leaderboard;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.leaderboard.types.UserLeaderboardType;
import dev.astro.net.user.User;
import dev.astro.net.user.UserManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class LeaderboardManager {

    private final UserManager userManager;
    private final Table<String, UserLeaderboardType, Long> userLeaderboards;

    public LeaderboardManager(Comet plugin) {
        this.userManager = plugin.getUserManager();

        this.userLeaderboards = HashBasedTable.create();
        this.updateLeaderboard();

        LeaderboardRunnable leaderboardRunnable = new LeaderboardRunnable(this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                CometPlugin.getPlugin(),
                leaderboardRunnable,
                20L * 3600L,
                20L * 3600L
        );
    }

    private void updateUserLeaderboard() {
        this.userLeaderboards.clear();

        for (User user : userManager.getDatabase().getUsersFromDB()) {
            String userName = user.getName();

            if (user.getParkourTime() > 0) userLeaderboards.put(userName, UserLeaderboardType.PARKOUR_TIME, user.getParkourTime());
        }
    }

    public void updateLeaderboard() {
        this.updateUserLeaderboard();
    }

    public Map<String, Long> getSortedUserLeaderboard(UserLeaderboardType type) {
        return userLeaderboards.column(type).entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
