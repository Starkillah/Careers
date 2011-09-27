package org.blockface.careers.events;

import org.blockface.careers.locale.Logging;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
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


}
