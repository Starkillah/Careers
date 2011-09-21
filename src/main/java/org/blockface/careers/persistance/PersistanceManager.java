package org.blockface.careers.persistance;

import org.blockface.careers.Careers;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;

public class PersistanceManager {

    private static Database db;

    public static void load() {
        db = new YAMLdb(Careers.getInstance());
    }

    public static Job getJob(String player) {
        return db.getJob(player);
    }

    public static void saveJob(Job job) {
        db.saveJob(job);
    }

    public static void unload() {
        saveAll();
        db.unload();
    }

    public static void saveAll() {
        for(Job j : JobsManager.getAllJobs()) {
            saveJob(j);
        }
    }
}
