package org.blockface.careers.objects;

public class Crime {

    public String getCriminal() {
        return criminal;
    }

    public TYPE getType() {
        return crimeType;
    }

    public enum TYPE {
        MURDER,
        THEFT,
        ASSAULT,
        POISONING
    }
    private TYPE crimeType;
    private String criminal;

    public Crime(TYPE type, String player) {
        this.crimeType = type;
        this.criminal = player;
    }

}
