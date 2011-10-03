package org.blockface.careers.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Doctor extends GenericJob {

    public Doctor() {
        loadInfo();
    }

    public Doctor(String player) {
        super(player);
        loadInfo();
    }

    private void loadInfo() {
        this.name = "Doctor";
        this.pluralName = "Doctors";
        this.abilities.add(ABILITIES.HEAL);
        this.color = ChatColor.DARK_PURPLE;
    }

    @Override
    public void printInfo(Player player) {
        super.printInfo(player);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "No Special Yet: " + getAbilityChance() + "%");
    }
}
