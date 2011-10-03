package org.blockface.careers.tasks;

import org.blockface.careers.managers.JailManager;
import org.bukkit.entity.Player;

public class FreeJailed implements Runnable{

    private String player;

    public FreeJailed(String  player) {
        this.player = player;
    }

    public void run() {
        JailManager.freeInmate(player);
    }
}
