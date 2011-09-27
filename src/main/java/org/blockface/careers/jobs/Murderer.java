package org.blockface.careers.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Murderer extends GenericJob {
    public Murderer() {
        loadInfo();
    }

    public Murderer(String player) {
        super(player);
        loadInfo();
    }

    private void loadInfo() {
        this.name = "Murderer";
        this.pluralName = "Murderers";
        this.abilities.add(ABILITIES.KILL);
        this.color = ChatColor.DARK_RED;
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Critical Hit Chance: " + this.getAbilityChance() + "%");
    }
}
