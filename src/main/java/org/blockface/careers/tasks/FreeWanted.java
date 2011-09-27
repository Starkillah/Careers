package org.blockface.careers.tasks;

import org.blockface.careers.managers.CrimeManager;

public class FreeWanted implements Runnable {
    private String player;

    public FreeWanted(String player) {
        this.player = player;
    }

    public void run() {
        CrimeManager.escapedWanted(player);
    }
}
