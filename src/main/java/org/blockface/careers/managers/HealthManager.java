package org.blockface.careers.managers;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.bukkit.entity.Player;

public class HealthManager {

    public static void healPlayer(Player patient, Player doctor, Job job) {
        int hearts = 20-patient.getHealth();
        double cost = hearts* Config.getHealCost();
        if(PoisonManager.isPoisoned(patient)) cost += Config.getPoisonCureCost();
        if(cost==0) return;
        if(!EconomyManager.pay(patient,doctor,cost,"healing")) return;
        patient.setHealth(20);
        PoisonManager.curePoison(patient);
        job.addExperience();
    }

}
