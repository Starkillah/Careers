package org.blockface.careers.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface Job {

    public enum ABILITIES {
        HEAL,
        CURE,
        LOCKPICK,
        ANTIMOB,
        KILL,
        POISON,
        SUPERSMELT,
        PICKPOCKET,
        ARREST
    }

    public String getName();

    public String getFormattedName();

    public String getPluralName();

    public ChatColor getColor();

    public String getPlayer();

    public double getAbilityChance();

    public void setPlayer(String player);

    public Boolean hasAbility(ABILITIES type);

    public int getExperience();

    public void addExperience();

    public void setExperience(int amount);

    public void addExperience(int amount);

    public int getLevel();

    public void printInfo(Player player);

    public void applyTitle();

}
