package org.blockface.careers.events;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.blockface.careers.locale.Logging;
import org.blockface.careers.managers.CrimeManager;
import org.blockface.careers.managers.JailManager;
import org.blockface.careers.managers.WitnessManager;
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
            Inmate inmate = JailManager.getInmate(event.getPlayer());
            Language.JAIL_TIME.bad(event.getPlayer(),inmate.getTimeLeft());
        }

        //See if switch attempt
        if(event.isCancelled())
            if(event.getAction()== Action.LEFT_CLICK_BLOCK||event.getAction()==Action.RIGHT_CLICK_BLOCK)
                if(Config.isSwitchable(event.getClickedBlock().getTypeId()))
                    event.setCancelled(!CareersEvents.canSwitch(event.getPlayer()));



    }

    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if(CrimeManager.isWanted(event.getPlayer().getName()) || JailManager.isJailed(event.getPlayer())) {
            Language.TELEPORT.bad(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player) CareersEvents.onPoke(event.getPlayer(),(Player)event.getRightClicked());
    }

    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(JailManager.isJailed(event.getPlayer()))
            event.setRespawnLocation(JailManager.getJail());
    }
}
