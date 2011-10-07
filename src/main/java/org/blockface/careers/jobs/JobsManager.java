package org.blockface.careers.jobs;

import org.blockface.careers.locale.Logging;
import org.blockface.careers.persistance.PersistanceManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

public class JobsManager {

    private static HashMap<String,Job> JOBS = new HashMap<String, Job>();

    public static Collection<Job> getAllJobs() {
        return JOBS.values();
    }

    public static Job constructJob(String job,String player) {
        Job ret = new GenericJob(player);
        if(job.equalsIgnoreCase("Thief")) ret = new Thief(player);
        if(job.equalsIgnoreCase("Murderer")) ret = new Murderer(player);
        if(job.equalsIgnoreCase("Officer")) ret = new Officer(player);
        if(job.equalsIgnoreCase("Knight")) ret = new Knight(player);
        if(job.equalsIgnoreCase("Assassin")) ret = new Assassin(player);
        if(job.equalsIgnoreCase("Doctor")) ret = new Doctor(player);
        if(job.equalsIgnoreCase("Boss")) ret = new Boss(player);
        return ret;
    }

    public static Job getJob(Player player) {
        return getJob(player.getName());
    }

    public static Job getJob(String player) {
        if(JOBS.containsKey(player)) return JOBS.get(player);
        Job j = PersistanceManager.getJob(player);
        JOBS.put(player,j);
        return j;
    }

    public static void setJob(Job job) {
        JOBS.put(job.getPlayer(), job);
        PersistanceManager.saveJob(job);
        job.applyTitle();
        job.printInfo(Bukkit.getServer().getPlayer(job.getPlayer()));
    }

}
