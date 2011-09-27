package org.blockface.careers.managers;

import org.blockface.careers.Careers;
import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.blockface.careers.tasks.KillPoisoned;
import org.blockface.careers.util.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class PoisonManager {
    private static HashSet<String> poisonedPlayers = new HashSet<String>();

    public static boolean isPoisoned(Player player) {
        return poisonedPlayers.contains(player.getName());
    }

    public static void poisonPlayer(Player victim, Player attacker, Job ja) {
        if(!ja.hasAbility(Job.ABILITIES.POISON)) return;
        attacker.getItemInHand().setAmount(attacker.getItemInHand().getAmount()-1);
        if(Tools.randBoolean(ja.getAbilityChance())) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Careers.getInstance(),new KillPoisoned(victim,attacker),20L* Config.getFastPoisonTime());
            Language.POISONED_FAST.good(attacker,victim.getDisplayName());}
        else {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Careers.getInstance(),new KillPoisoned(victim,attacker),20L* Config.getPoisonTime());
            Language.POISONED_SUCCESS.good(attacker,victim.getDisplayName());}

        Language.POISONED.bad(victim);

    }

    public static void curePoison(Player player) {

    }

    public static void killPoisoned(Player victim, Player attacker) {
        poisonedPlayers.remove(victim.getName());
        victim.damage(10000);
        Language.POISONED_DEATH.good(attacker,victim.getDisplayName());
        EconomyManager.payAll(victim,attacker);
    }


}
