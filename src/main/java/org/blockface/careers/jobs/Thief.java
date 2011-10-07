package org.blockface.careers.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Thief extends GenericJob {

    public Thief() {
        loadInfo();
    }

    public Thief(String player) {
        super(player);
        loadInfo();
    }

    private void loadInfo() {
        this.name = "Thief";
        this.pluralName = "Thieves";
        this.abilities.add(ABILITIES.LOCKPICK);
        this.abilities.add(ABILITIES.PICKPOCKET);
        this.color = ChatColor.GRAY;
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Lock Pick Chance: " + getAbilityChance() + "%");
    }
}
