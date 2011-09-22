package org.blockface.careers.tasks;

import org.blockface.careers.managers.JailManager;
import org.bukkit.entity.Player;

public class FreeJailed implements Runnable{

    private Player player;

    public FreeJailed(Player player) {
        this.player = player;
    }

    public void run() {
        if(!player.isOnline()) return;
        JailManager.freeInmate(player);
    }
}
