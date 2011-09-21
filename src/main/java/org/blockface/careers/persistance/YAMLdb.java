package org.blockface.careers.persistance;


import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

import java.io.File;

public class YAMLdb implements Database {

    Configuration data;

    public YAMLdb(Plugin plugin) {
        data = new Configuration(new File(plugin.getDataFolder(),"data.yml"));
        data.load();
    }

    public Job getJob(String player) {
        Job ret = JobsManager.constructJob(data.getString("players." + player + ".job","Bum"),player);
        ret.setExperience(data.getInt("players." + player + ".exp",1));
        return ret;
    }

    public void saveJob(Job job) {
        data.setProperty("players." + job.getPlayer() + ".job",job.getName());
        data.setProperty("players." + job.getPlayer() + ".exp", job.getExperience());
    }

    public void unload() {
        data.save();
    }
}
