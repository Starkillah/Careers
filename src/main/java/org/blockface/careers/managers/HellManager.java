package org.blockface.careers.managers;


import org.blockface.careers.Careers;
import org.blockface.careers.config.Config;
import org.blockface.careers.locale.Language;
import org.blockface.careers.tasks.FreeDead;
import org.blockface.careers.util.Tools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.tools.Tool;
import java.util.HashSet;

public class HellManager {

    private static HashSet<String> dead = new HashSet<String>();
    private static Location hell;

    public static void load() {
        for(World world : Bukkit.getServer().getWorlds()) {
            if(world.getEnvironment() != World.Environment.NETHER) continue;
            hell = world.getSpawnLocation();
            hell.setY(129);
        }
    }

    public static void setDead(Player player) {
        if(isDead(player.getName())) return;
        dead.add(player.getName());
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Careers.getInstance(),new FreeDead(player.getName()), Config.getHellTime() * 20L);
        Language.IN_HELL.bad(player,Config.getHellTime());
    }

    public static Location getHell() {
        return hell;
    }

    public static void freeDead(String name) {
        if(!isDead(name)) return;
        dead.remove(name);
        Player player = Tools.getPlayer(name);
        if(!player.isOnline()) return;
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
    }

    public static boolean isDead(String player) {
        return dead.contains(player);
    }


}
