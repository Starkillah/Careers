package org.blockface.careers.util;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Tools {

    public static boolean randBoolean(double p) {
        if(p>1) p=p/100;
        return (Math.random() < p);
    }

    public static boolean isNight(Location location) {
        World world = location.getWorld();
        return world.getTime() > 14000;
    }

    public static Player getPlayer(String name) {
        return Bukkit.getServer().getPlayer(name);
    }

}
