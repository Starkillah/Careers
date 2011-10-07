package org.blockface.careers.events;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.blockface.careers.locale.Logging;
import org.blockface.careers.managers.*;
import org.blockface.careers.objects.Inmate;
import org.blockface.careers.objects.Witness;
import org.blockface.careers.util.Tools;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

public class PlayerEvents extends PlayerListener {
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Job j = JobsManager.getJob(event.getPlayer());
        j.applyTitle();
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        Witness witness = WitnessManager.getWitness(event.getPlayer().getName());
        witness.parseChat(event.getMessage());
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(JailManager.isJailed(event.getPlayer())) {
            event.setCancelled(true);
            Inmate inmate = JailManager.getInmate(event.getPlayer().getName());
            Language.JAIL_TIME.bad(event.getPlayer(),inmate.getTimeLeft());
            event.getPlayer().teleport(JailManager.getJail());
        }

        //See if switch attempt
        if(event.isCancelled())
            if(event.getAction()== Action.LEFT_CLICK_BLOCK||event.getAction()==Action.RIGHT_CLICK_BLOCK)
                if(Config.isSwitchable(event.getClickedBlock().getTypeId()))
                    event.setCancelled(!CareersEvents.canSwitch(event.getPlayer()));



    }

    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if(event.getFrom().getWorld().equals(event.getTo().getWorld()) && event.getFrom().distance(event.getTo()) < 5) return;
        if(CrimeManager.isWanted(event.getPlayer().getName()) || PoisonManager.isPoisoned(event.getPlayer())) {
            Language.TELEPORT.bad(event.getPlayer());
            return;}

        if(JailManager.isJailed(event.getPlayer())) {
            Language.TELEPORT.bad(event.getPlayer());
            event.setTo(JailManager.getJail());
            return;}

        if(HellManager.isDead(event.getPlayer().getName())) {
            Language.TELEPORT.bad(event.getPlayer());
            event.setTo(HellManager.getHell());}

    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player && !JailManager.isJailed(event.getPlayer())) CareersEvents.onPoke(event.getPlayer(),(Player)event.getRightClicked());
    }

    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(JailManager.isJailed(event.getPlayer())){
            event.setRespawnLocation(JailManager.getJail());
            return;}

        HellManager.setDead(event.getPlayer());
        event.setRespawnLocation(HellManager.getHell());
    }


}
