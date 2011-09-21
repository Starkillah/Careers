package org.blockface.careers.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class ProvokeManager {
    private static HashMap<String,HashSet<String>> provokedByList = new HashMap<String, HashSet<String>>();

    public static void addProvoker(Player victim, Player provoker) {
        getProvokers(victim.getName()).add(provoker.getName());
    }

    public static boolean isProvoker(Player victim, Player provoker) {
        return getProvokers(victim.getName()).contains(provoker.getName());
    }

    public static HashSet<String> getProvokers(String victim) {
        if(provokedByList.containsKey(victim)) return provokedByList.get(victim);
        HashSet<String> provokers = new HashSet<String>();
        provokedByList.put(victim,provokers);
        return provokers;
    }

}
