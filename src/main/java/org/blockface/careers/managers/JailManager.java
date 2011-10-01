package org.blockface.careers.managers;

import org.blockface.careers.Careers;
import org.blockface.careers.config.Config;
import org.blockface.careers.locale.Language;
import org.blockface.careers.objects.Crime;
import org.blockface.careers.objects.Inmate;
import org.blockface.careers.persistance.PersistanceManager;
import org.blockface.careers.tasks.FreeJailed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class JailManager {

	private static HashMap<Player,Inmate> jailed = new HashMap<Player,Inmate>();
    private static Location jail;

    public static void load() {
        jail = PersistanceManager.getJail();
    }

    public static Location getJail() {
        return jail;
    }

    public static void setJail(Location location) {
        jail = location;
        PersistanceManager.setJail(jail);
    }

    public static boolean isJailed(Player player) {
        return jailed.containsKey(player);
    }

    public static Inmate getInmate(Player player) {
        return jailed.get(player);
    }

    public static void arrestPlayer(Player criminal, Player officer) {
        Crime crime = CrimeManager.getWanted(criminal.getName());
        CrimeManager.removeWanted(criminal.getName());
        dropInventory(criminal);
        int time = 3;
        /*
        if(crime.getType() == Crime.CrimeType.MURDER) time = CareerConfig.GetMurderSentence();
        if(crime.getType() == Crime.CrimeType.THEFT) time = CareerConfig.GetTheftSentence();
        if(crime.getType() == Crime.CrimeType.WEAPONS) time = CareerConfig.GetWeaponsSentence();
        */
        criminal.teleport(jail);
		jailed.put(criminal, new Inmate(criminal, crime, time));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Careers.getInstance(), new FreeJailed(criminal), 20L * time * 60);
        Language.ARRESTED.broadcastGood(criminal.getName(),crime.getType().name().toLowerCase());
        EconomyManager.payWage(officer, Config.getOfficerWage());
    }

    private static void dropInventory(Player player)
    {
        ItemStack[] contents = player.getInventory().getContents().clone();
        Location l = player.getLocation();
        player.getInventory().clear();
        for(ItemStack item : contents)
        {
            if(item == null) continue;
            player.getWorld().dropItem(l,item);
        }
    }

    public static void freeInmate(Player player) {
        Inmate inmate = jailed.get(player);
        jailed.remove(player);
        if(!player.isOnline()) return;
        player.teleport(inmate.getHome());
        Language.FREED.good(player);
    }


}
