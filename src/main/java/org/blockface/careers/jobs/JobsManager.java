package org.blockface.careers.jobs;

import org.blockface.careers.locale.Logging;
import org.blockface.careers.persistance.PersistanceManager;
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
        JOBS.put(job.getPlayer(),job);
        PersistanceManager.saveJob(job);
        Logging.info(job.getPlayer() + " is now a " + job.getName());
    }

}
