package org.blockface.careers.tasks;

import org.blockface.careers.managers.PoisonManager;
import org.bukkit.entity.Player;

public class KillPoisoned implements Runnable{

    private Player victim;
    private Player attacker;

    public KillPoisoned(Player victim,Player attacker) {
        this.victim = victim;
    }

    public void run() {
        PoisonManager.killPoisoned(victim,attacker);
    }
}
