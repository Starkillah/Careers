package org.blockface.careers.managers;

import org.blockface.careers.Careers;
import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.blockface.careers.objects.Crime;
import org.blockface.careers.tasks.KillPoisoned;
import org.blockface.careers.util.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class PoisonManager {
    private static HashSet<String> poisonedPlayers = new HashSet<String>();

    public static boolean isPoisoned(Player player) {
        return poisonedPlayers.contains(player.getName());
    }

    public static void poisonPlayer(Player victim, Player attacker, Job ja) {
        if(!Tools.isNight(attacker.getLocation())) {
            Language.NIGHT_ONLY.bad(attacker);
            return;}
        ItemStack shrooms = attacker.getItemInHand();
        if(shrooms.getAmount()==1){
            attacker.setItemInHand(null);
            return;}
        shrooms.setAmount(shrooms.getAmount() - 1);
        if(Tools.randBoolean(ja.getAbilityChance())) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Careers.getInstance(),new KillPoisoned(victim,attacker),20L* Config.getFastPoisonTime());
            Language.POISONED_FAST.good(attacker,victim.getDisplayName());}
        else {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Careers.getInstance(),new KillPoisoned(victim,attacker),20L * Config.getPoisonTime());
            Language.POISONED_SUCCESS.good(attacker,victim.getDisplayName());}
        poisonedPlayers.add(victim.getName());
        Language.POISONED.bad(victim);
        CrimeManager.alertWitnesses(attacker,victim, Crime.TYPE.POISONING);

    }

    public static void curePoison(Player player) {
        if(!poisonedPlayers.remove(player)) return;
        Language.POISONED_CURED.good(player);
    }

    public static void killPoisoned(Player victim, Player attacker) {
        if(!poisonedPlayers.remove(victim.getName())) return;
        victim.damage(10000);
        Language.POISONED_DEATH.good(attacker,victim.getDisplayName());
        Job job = JobsManager.getJob(attacker);
        job.addExperience();
        EconomyManager.payAll(victim,attacker);
    }


}
