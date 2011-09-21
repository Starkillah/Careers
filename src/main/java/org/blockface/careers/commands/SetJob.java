package org.blockface.careers.commands;

import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetJob implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(strings.length < 1) return false;
        if(!(sender instanceof Player)) {
            Language.IN_GAME_ONLY.bad(sender);
            return true;
        }
        Player player = (Player)sender;
        JobsManager.setJob(JobsManager.constructJob(strings[0],player.getName()));
        return true;
    }

}
