package org.blockface.careers.managers;

import org.blockface.careers.objects.Witness;

import java.util.HashMap;

public class WitnessManager {
    private static HashMap<String,Witness> witnesses = new HashMap<String, Witness>();

    public static Witness getWitness(String player) {
        if(witnesses.containsKey(player)) return witnesses.get(player);
        Witness w = new Witness(player);
        witnesses.put(player, w);
        return w;
    }


}
