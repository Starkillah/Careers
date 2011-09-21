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
        this.color = ChatColor.GRAY;
    }

    public int getPickChance() {
        float level = this.getLevel();
        return (int)(50*(1-6/(level+6)));
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Lock Pick Chance: " + getPickChance() + "%");
    }
}
