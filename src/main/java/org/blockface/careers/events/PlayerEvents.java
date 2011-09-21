package org.blockface.careers.events;

import org.blockface.careers.config.Config;
import org.blockface.careers.jobs.Job;
import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.util.Tools;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerEvents extends PlayerListener {
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Job j = JobsManager.getJob(event.getPlayer());
        event.getPlayer().setDisplayName(j.getFormattedName() + event.getPlayer().getName());
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!player.getName().equalsIgnoreCase("SwearWord")) return;
        Job j = JobsManager.getJob(player);
        j.addExperience();
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        //See if switch attempt
        if(event.isCancelled())
            if(event.getAction()== Action.LEFT_CLICK_BLOCK||event.getAction()==Action.RIGHT_CLICK_BLOCK)
                if(Config.isSwitchable(event.getClickedBlock().getTypeId()))
                    event.setCancelled(!CareersEvents.onSwitchAttempt(event.getPlayer()));


    }
}
