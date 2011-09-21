package org.blockface.careers.jobs;


import com.sun.org.apache.xml.internal.resolver.helpers.Debug;
import org.blockface.careers.locale.Language;
import org.blockface.careers.persistance.PersistanceManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class GenericJob implements Job{

    protected String name = "Bum";
    protected String pluralName = "Bums";
    protected ChatColor color = ChatColor.WHITE;
    protected String player;

    protected int exp=1;
    protected int expGained=1;

    protected HashSet<ABILITIES> abilities = new HashSet<ABILITIES>();

    public GenericJob(String player) {
        this.player = player;
    }

    public GenericJob() {

    }

    public GenericJob(Player player) {
        this.player = player.getName();
    }

    public String getName() {
        return this.name;
    }

    public String getFormattedName() {
        return this.color + this.name;
    }

    public String getPluralName() {
        return this.pluralName;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getPlayer() {
        return this.player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Boolean hasAbility(ABILITIES type) {
        return this.abilities.contains(type);
    }

    public int getExperience() {
        return this.exp;
    }

    public void addExperience() {
        addExperience(this.expGained);

    }

    public void addExperience(int amount) {
        int level = getLevel();
        this.exp += amount;
        int newlevel = getLevel();
        if(newlevel == level) return;
        Language.LEVELUP.good(Bukkit.getServer().getPlayer(this.player),newlevel);
        PersistanceManager.saveJob(this);
    }

    public void setExperience(int amount) {
        this.exp = amount;
    }

    public int getLevel() {
        return (int)Math.sqrt(this.exp);
    }

    public void printInfo(Player player) {
        if(player == null) return;
        player.sendMessage(ChatColor.DARK_GRAY + "|-----------------| " + this.getFormattedName() + ChatColor.DARK_GRAY + " |-----------------|");
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN+"Level: " + this.getLevel());
        int newxp = (int)Math.pow(this.getLevel()+1,2);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN+"Experience Points: " + this.exp + " / " + newxp);
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN+"Next Level: " + (newxp-this.exp));
    }
}