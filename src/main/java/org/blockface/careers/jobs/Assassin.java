package org.blockface.careers.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Assassin extends GenericJob {

    public Assassin() {
        loadInfo();
    }

    public Assassin(String player) {
        super(player);
        loadInfo();
    }

    private void loadInfo() {
        this.name = "Assassin";
        this.pluralName = "Assassins";
        this.abilities.add(ABILITIES.POISON);
        this.color = ChatColor.RED;
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Fast Poison Chance: " + getAbilityChance() + "%");
    }
}
