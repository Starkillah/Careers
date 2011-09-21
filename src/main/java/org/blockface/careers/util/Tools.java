package org.blockface.careers.util;


import org.bukkit.Location;
import org.bukkit.World;

public class Tools {

    public static boolean randBoolean(double p) {
        if(p>1) p=p/100;
        return (Math.random() < p);
    }

    public static boolean isNight(Location location) {
        World world = location.getWorld();
        return world.getTime() > 14000;
    }

}
