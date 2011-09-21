package org.blockface.careers.events;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class EntityEvents extends EntityListener{

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        //Check for PVP
        if(event.getEntity() instanceof Player && event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent)event;
            if(subEvent.getDamager() instanceof Player) {
                Player victim = (Player)event.getEntity();
                Player attacker = (Player)subEvent.getDamager();
                event.setCancelled(!CareersEvents.canPVP(attacker,victim));
            }
        }


    }
}
