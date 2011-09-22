package org.blockface.careers.persistance;

import org.blockface.careers.jobs.Job;
import org.bukkit.Location;


public interface Database {

    public Job getJob(String player);

    public void saveJob(Job job);

    public void setJail(Location location);

    public Location getJail();

    public void unload();
}
