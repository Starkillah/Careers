package org.blockface.careers.managers;

import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.blockface.careers.locale.Logging;
import org.blockface.careers.objects.Crime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CrimeManager {

    private static HashMap<String,Crime> wanted = new HashMap<String, Crime>();

    public static boolean isWanted(String player) {
        return wanted.containsKey(player);
    }

    public static void addWanted(String player, Crime.TYPE type) {
        Logging.info("Setting " + player + " as wanted.");
        Crime c = new Crime(type,player);
        wanted.put(player,c);
        Language.WANTED.broadcastBad(player,type.name().toLowerCase());
    }

    public static Crime getWanted(String player) {
        return wanted.get(player);
    }

    public static void alertWitnesses(Player criminal,Player victim, Crime.TYPE type) {
        for(Entity e : criminal.getNearbyEntities(25, 25, 25))
		{
			if(!(e instanceof Player)) continue;
            Player p = (Player)e;
				if(p.equals(victim)) continue;
				if(JobsManager.getJob(p).hasAbility(Job.ABILITIES.ARREST)) {
					addWanted(criminal.getName(), type);
					return;
				} else {
                    WitnessManager.getWitness(p.getName()).addCriminal(criminal.getName(),type);
                    Language.WITNESSED.bad(p,criminal.getName(),type.name().toLowerCase());
                    Language.SAW_YOU.bad(criminal,p.getName(),type.name().toLowerCase());
				}}
    }

    public static void removeWanted(String name) {
        wanted.remove(name);
    }
}

