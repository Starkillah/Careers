package org.blockface.careers.events;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.*;
import org.blockface.careers.locale.Language;
import org.blockface.careers.locale.Logging;
import org.blockface.careers.managers.CrimeManager;
import org.blockface.careers.managers.JailManager;
import org.blockface.careers.managers.ProvokeManager;
import org.blockface.careers.objects.Crime;
import org.blockface.careers.util.Tools;
import org.bukkit.entity.Player;

public class CareersEvents {

    public static boolean canSwitch(Player player) {
        Job job = JobsManager.getJob(player);
        if(!job.hasAbility(Job.ABILITIES.LOCKPICK)) return false;
        Thief thief = (Thief)job;
        Boolean res = Tools.randBoolean(thief.getPickChance());
        if(res) {
            thief.addExperience();
            Language.THEFT_SUCCEEDED.good(player);
        }
        else {
            Language.THEFT_FAILED.bad(player);
            player.damage(Config.getThiefDamage());
        }

        return res;
    }

    public static boolean canPVP(Player attacker, Player victim) {
        if(!Tools.isNight(attacker.getLocation())) return false;
        Job ja = JobsManager.getJob(attacker);
        Job jv = JobsManager.getJob(victim);
        //Return if cannot kill
        if(!ja.hasAbility(Job.ABILITIES.KILL) && !ProvokeManager.isProvoker(attacker,victim)) return false;
        if(ja.hasAbility(Job.ABILITIES.KILL)) {
            Murderer murderer = (Murderer)ja;
            if(Tools.randBoolean(murderer.getCriticalHitChance())) {
                victim.damage(2);
                Language.CRITICAL_HIT.good(attacker);
            }}
        if(jv.hasAbility(Job.ABILITIES.ARREST)) {
            if(!CrimeManager.isWanted(attacker.getName())) CrimeManager.addWanted(attacker.getName(), Crime.TYPE.ASSAULT);
            Officer officer = (Officer)jv;
            if(Tools.randBoolean(officer.getDodgeChance())) {
                Language.DODGED.good(victim);
                Language.WAS_DODGED.bad(attacker);
                return false;
            }
        }
        ProvokeManager.addProvoker(victim,attacker);
        return true;
    }

    public static void onPlayerDeath(Player attacker, Player victim) {
        Logging.info("Death fired.");
        Job ja = JobsManager.getJob(attacker);
        if(ja instanceof Murderer) {
            ja.addExperience();
            CrimeManager.alertWitnesses(attacker,victim, Crime.TYPE.MURDER);
        }

    }

    public static void onPoke(Player player, Player rightClicked) {
        Job jp = JobsManager.getJob(player);
        Job jrc = JobsManager.getJob(rightClicked);
        if(jp.hasAbility(Job.ABILITIES.ARREST) && CrimeManager.isWanted(rightClicked.getName())) {
            JailManager.arrestPlayer(rightClicked,player);
            jp.addExperience();
        }
    }
}
