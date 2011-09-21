package org.blockface.careers.jobs;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Officer extends GenericJob{

    public Officer() {
        loadInfo();
    }

    public Officer(String player) {
        super(player);
        loadInfo();
    }

    private void loadInfo() {
        this.name = "Officer";
        this.pluralName = "Officers";
        this.abilities.add(ABILITIES.ARREST);
        this.color = ChatColor.BLUE;
    }

    public int getDodgeChance() {
        float level = this.getLevel();
        return (int)(50*(1-6/(level+6)));
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Dodge Chance: " + getDodgeChance() + "%");
    }
}
