package org.blockface.careers.commands;

import org.blockface.careers.jobs.JobsManager;
import org.blockface.careers.locale.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddExp implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(strings.length < 1) return false;
        if(!(sender instanceof Player)) {
            Language.IN_GAME_ONLY.bad(sender);
            return false;
        }
        int amount = parseInt(strings[0]);
        JobsManager.getJob(sender.getName()).addExperience(amount);
        Language.EXP_ADDED.good(sender,amount);
        return true;
    }

    public int parseInt(String s) {
        try{
            return Integer.parseInt(s);
        }
        catch (NumberFormatException exception) {
            return 0;
        }
    }

}
