package org.blockface.careers.persistance;


import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    public void setJail(Location location) {
        data.setProperty("jail.world",location.getWorld().getName());
        data.setProperty("jail.x",location.getBlockX());
        data.setProperty("jail.y",location.getBlockY()+1);
        data.setProperty("jail.z",location.getBlockZ());
    }

    public Location getJail() {
        int x = data.getInt("jail.x",0);
        int y = data.getInt("jail.y",0);
        int z = data.getInt("jail.z",0);
        String world = data.getString("jail.world",Bukkit.getServer().getWorlds().get(0).getName());
        return new Location(Bukkit.getServer().getWorld(world),x,y,z);
    }

    public void unload() {
        data.save();
    }
}
