package org.blockface.careers.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Blacksmith extends GenericJob {

    public Blacksmith() {
        loadInfo();
    }

    public Blacksmith(String player) {
        super(player);
        loadInfo();
    }

    private void loadInfo() {
        this.name = "Blacksmith";
        this.pluralName = "Blacksmiths";
        this.abilities.add(ABILITIES.SUPERSMELT);
        this.color = ChatColor.AQUA;
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "KO Chance: " + getAbilityChance() + "%");
    }
}
