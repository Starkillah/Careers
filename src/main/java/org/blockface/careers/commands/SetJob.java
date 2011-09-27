package org.blockface.careers.commands;

import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetJob implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(strings.length < 1) return false;
        if(!(sender instanceof Player) && strings.length < 2) {
            Language.IN_GAME_ONLY.bad(sender);
            return true;
        }
        if(strings.length==1) {
            Player player = (Player)sender;
            JobsManager.setJob(JobsManager.constructJob(strings[0],player.getName()));
        }
        else JobsManager.setJob(JobsManager.constructJob(strings[1], matchPlayer(strings[0])));
        return true;
    }

    public String matchPlayer(String player) {
        return Bukkit.getServer().matchPlayer(player).get(0).getName();
    }

}
