package org.blockface.careers.events;

import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Logging;
import org.blockface.careers.util.Tools;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;
import sun.rmi.runtime.Log;

public class EntityEvents extends EntityListener{

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.isCancelled()) return;
        //Check for PVP
        if(event.getEntity() instanceof Player && event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent)event;
            if(subEvent.getDamager() instanceof Player) {
                Player victim = (Player)event.getEntity();
                Player attacker = (Player)subEvent.getDamager();
                event.setCancelled(!CareersEvents.canPVP(attacker,victim));
                if(!event.isCancelled())
                    if(victim.getHealth()-subEvent.getDamage() < 1)
                        CareersEvents.onPlayerDeath(attacker,victim);

            }}
        else if(event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent)event.getEntity().getLastDamageCause();
                if(subEvent.getDamager() instanceof Player)
                    CareersEvents.onMobDamage(event.getEntity(),(Player)subEvent.getDamager(),subEvent.getDamage());

        }
    }

    @Override
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player)event.getEntity();
        Job job = JobsManager.getJob(player);
        if(job.hasAbility(Job.ABILITIES.HEAL))
            if(Tools.randBoolean(job.getAbilityChance())) event.setAmount(event.getAmount()+3);

    }
}
