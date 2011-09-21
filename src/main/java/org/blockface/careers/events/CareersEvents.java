package org.blockface.careers.events;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.jobs.Murderer;
import org.blockface.careers.jobs.Thief;
import org.blockface.careers.locale.Language;
import org.blockface.careers.managers.ProvokeManager;
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
            }
        }

        return true;
    }

}
