package org.blockface.careers.managers;


import org.blockface.careers.Careers;
import org.blockface.careers.config.Config;
import org.blockface.careers.locale.Language;
import org.blockface.careers.tasks.FreeDead;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class HellManager {

    private static HashSet<Player> dead = new HashSet<Player>();
    private static Location hell;

    public static void load() {
        for(World world : Bukkit.getServer().getWorlds()) {
            if(world.getEnvironment() != World.Environment.NETHER) continue;
            hell = world.getSpawnLocation();
            hell.setY(129);
        }
    }

    public static void setDead(Player player) {
        if(isDead(player)) return;
        dead.add(player);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Careers.getInstance(),new FreeDead(player), Config.getHellTime() * 20L);
        Language.IN_HELL.bad(player,Config.getHellTime());
    }

    public static Location getHell() {
        return hell;
    }

    public static void freeDead(Player player) {
        if(!isDead(player)) return;
        dead.remove(player);
        if(!player.isOnline()) return;
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
    }

    public static boolean isDead(Player player) {
        return dead.contains(player);
    }


}
