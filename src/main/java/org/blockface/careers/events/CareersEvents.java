package org.blockface.careers.events;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.jobs.Thief;
import org.blockface.careers.locale.Language;
import org.blockface.careers.util.Tools;
import org.bukkit.entity.Player;

public class CareersEvents {

    public static Boolean onSwitchAttempt(Player player) {
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

}
