package org.blockface.careers.objects;



import org.blockface.careers.locale.Language;
import org.blockface.careers.managers.CrimeManager;

import java.util.HashMap;

public class Witness {
    HashMap<String,Crime.TYPE> crimes = new HashMap<String, Crime.TYPE>();
    private String player;

    public Witness(String player) {
        this.player = player;
    }

    public void addCriminal(String player, Crime.TYPE type) {
        crimes.put(player,type);
    }

    public boolean isCriminal(String player) {
        return crimes.containsKey(player);
    }

    public void removeCriminal(String player) {
        crimes.remove(player);
    }

    public void clearAll() {
        crimes = new HashMap<String, Crime.TYPE>();
    }

    public void reportCriminal(String player) {
        CrimeManager.addWanted(player,crimes.get(player));
        removeCriminal(player);
    }

    public void parseChat(String chat) {
        if(crimes.size() < 1) return;
        chat = chat.toLowerCase();
        for(String player : crimes.keySet()) {
            if(!chat.contains(player.toLowerCase())) continue;
            reportCriminal(player);
            Language.REPORTED.good(player);
        }
    }

}
