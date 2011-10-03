package org.blockface.careers.events;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.*;
import org.blockface.careers.locale.Language;
import org.blockface.careers.managers.*;
import org.blockface.careers.objects.Crime;
import org.blockface.careers.util.Tools;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CareersEvents {

    public static boolean canSwitch(Player player) {
        if(JailManager.isJailed(player)) return false;
        Job job = JobsManager.getJob(player);
        if(!job.hasAbility(Job.ABILITIES.LOCKPICK)) return false;
        Boolean res = Tools.randBoolean(job.getAbilityChance());
        if(res) {
            job.addExperience();
            Language.THEFT_SUCCEEDED.good(player);
        }
        else {
            Language.THEFT_FAILED.bad(player);
            player.damage(Config.getThiefDamage());
        }
        CrimeManager.alertWitnesses(player,player, Crime.TYPE.THEFT);
        return res;
    }

    public static boolean canPVP(Player attacker, Player victim) {
        if(!Tools.isNight(attacker.getLocation())) return false;
        Job ja = JobsManager.getJob(attacker);
        Job jv = JobsManager.getJob(victim);
        //Return if cannot kill
        if(!ja.hasAbility(Job.ABILITIES.KILL) && !ProvokeManager.isProvoker(attacker,victim)) return false;
        if(ja.hasAbility(Job.ABILITIES.KILL)) {
            if(Tools.randBoolean(ja.getAbilityChance())) {
                victim.damage(2);
                Language.CRITICAL_HIT.good(attacker);
            }}
        if(jv.hasAbility(Job.ABILITIES.ARREST)) {
            if(!CrimeManager.isWanted(attacker.getName())) CrimeManager.addWanted(attacker.getName(), Crime.TYPE.ASSAULT);
            if(Tools.randBoolean(jv.getAbilityChance())) {
                Language.DODGED.good(victim);
                Language.WAS_DODGED.bad(attacker);
                return false;
            }
        }
        ProvokeManager.addProvoker(victim,attacker);
        return true;
    }

    public static void onPlayerDeath(Player attacker, Player victim) {
        Job ja = JobsManager.getJob(attacker);
        if(ja instanceof Murderer) {
            ja.addExperience();
            CrimeManager.alertWitnesses(attacker,victim, Crime.TYPE.MURDER);
        }
        EconomyManager.payAll(victim,attacker);
    }

    public static void onPoke(Player player, Player rightClicked) {
        Job jp = JobsManager.getJob(player);
        Job jrc = JobsManager.getJob(rightClicked);

        //Police Arrest
        if(jp.hasAbility(Job.ABILITIES.ARREST) && CrimeManager.isWanted(rightClicked.getName())) {
            JailManager.arrestPlayer(rightClicked,player);
            jp.addExperience();}

        //Poison
        if(jp.hasAbility(Job.ABILITIES.POISON) && (player.getItemInHand().getType().equals(Material.BROWN_MUSHROOM) || player.getItemInHand().getType().equals(Material.RED_MUSHROOM)) && !PoisonManager.isPoisoned(rightClicked)) {
            PoisonManager.poisonPlayer(rightClicked,player, jp);}

        //Heal
        if(jrc.hasAbility(Job.ABILITIES.HEAL)) {
            HealthManager.healPlayer(player,rightClicked,jrc);}
    }

    public static void onMobDamage(Entity entity, Player damager, int damage) {
        Job jd = JobsManager.getJob(damager.getName());
        if(!jd.hasAbility(Job.ABILITIES.ANTIMOB) || !(entity instanceof Creature)) return;
        Creature creature = (Creature)entity;
        if(Tools.randBoolean(jd.getAbilityChance())) creature.damage(1000);
        if(creature.getHealth() - damage <= 0) {
            EconomyManager.payWage(damager, Config.getKnightWage());
            jd.addExperience();}

    }
}
