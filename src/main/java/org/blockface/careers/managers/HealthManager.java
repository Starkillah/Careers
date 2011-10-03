package org.blockface.careers.managers;

import org.blockface.careers.config.Config;
import org.bukkit.entity.Player;

public class HealthManager {

    public static void healPlayer(Player patient, Player doctor) {
        int hearts = 20-patient.getHealth();
        double cost = hearts* Config.getHealCost();
        if(PoisonManager.isPoisoned(patient)) cost += Config.getPoisonCureCost();
        if(cost==0) return;
        if(!EconomyManager.pay(patient,doctor,cost,"healing")) return;
        patient.setHealth(20);
        PoisonManager.curePoison(patient);
    }

}
