package org.blockface.careers.tasks;

import org.blockface.careers.managers.HellManager;
import org.bukkit.entity.Player;

public class FreeDead implements Runnable{

    private Player player;

    public FreeDead(Player player) {
        this.player = player;
    }

    public void run() {
        HellManager.freeDead(player);
    }
}
