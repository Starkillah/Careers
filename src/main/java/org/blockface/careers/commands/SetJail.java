package org.blockface.careers.commands;

import org.blockface.careers.locale.Language;
import org.blockface.careers.managers.JailManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetJail implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            Language.IN_GAME_ONLY.bad(sender);
            return true;
        }
        Player player = (Player)sender;
        JailManager.setJail(player.getLocation());
        Language.SET_JAIL.good(player);
        return true;
    }
}
