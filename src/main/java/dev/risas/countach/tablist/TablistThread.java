package dev.risas.countach.tablist;

import dev.risas.countach.tablist.player.PlayerTablist;

public class TablistThread extends Thread {

    private final Tablist tablist;

    public TablistThread(Tablist tab) {
        setName("Tablist Thread");
        setDaemon(true);

        this.tablist = tab;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (PlayerTablist playerTablist : tablist.getPlayerTablist().values()) {
                    playerTablist.update();
                }
            }
            catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            try {
                Thread.sleep(250L);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
