package org.blockface.careers.config;

/**
 * @author dumptruckman, SwearWord
 */
public enum ConfigPath {
    LANGUAGE("settings.language_file", "english.yml", "# This is the language file you wish to use."),
    ISDEBUGGING("settings.isDebugging",true,"# Do not touch."),
    SWITCH_IDS("settings.switchIds","25,54,61,62,64,69,70,71,72,77,96,84,93,94", "# Switchable blocks"),
    THIEF_DAMAGE("settings.thiefDamage",1,"# Amount of damage done on failed pick."),
    ARREST_WAGE("settings.economy.arrestReward",50,"# Amonut to pay police officers."),
    ACCOUNT_NAME("settings.economy.accountName","SwearWord","# Account to take money from to pay Knights/Officers."),
    KNIGHT_WAGE("settings.economy.knightReward",5,"# Amount to pay knight for each mob kill.")
    ;


    private String path;
    private Object def;
    private String[] comments;

    ConfigPath(String path, Object def, String...comments) {
        this.path = path;
        this.def = def;
        this.comments = comments;
    }

    /**
     * Retrieves the path for a config option
     * @return The path for a config option
     */
    public String getPath() {
        return path;
    }

    /**
     * Retrieves the default value for a config path
     * @return The default value for a config path
     */
    public Object getDefault() {
        return def;
    }

    /**
     * Retrieves the comment for a config path
     * @return The comments for a config path
     */
    public String[] getComments() {
        if (comments != null) {
            return comments;
        }

        String[] comments = new String[1];
        comments[0] = "";
        return comments;
    }
}
