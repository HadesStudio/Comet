package dev.astro.net.model.leaderboard;

public class LeaderboardRunnable implements Runnable {

    private final LeaderboardManager leaderboardManager;

    public LeaderboardRunnable(LeaderboardManager leaderboardManager) {
        this.leaderboardManager = leaderboardManager;
    }

    @Override
    public void run() {
        leaderboardManager.updateLeaderboard();
    }
}
