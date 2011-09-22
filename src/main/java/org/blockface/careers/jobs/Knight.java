package org.blockface.careers.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Knight extends GenericJob {

    public Knight() {
        loadInfo();
    }

    public Knight(String player) {
        super(player);
        loadInfo();
    }

    private void loadInfo() {
        this.name = "Knight";
        this.pluralName = "Knights";
        this.abilities.add(ABILITIES.ANTIMOB);
        this.color = ChatColor.YELLOW;
    }

    public int getKOChance() {
        float level = this.getLevel();
        return (int)(50*(1-6/(level+6)));
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "KO Chance: " + getKOChance() + "%");
    }
}
