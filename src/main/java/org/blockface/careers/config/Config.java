package org.blockface.careers.config;

import org.blockface.careers.Careers;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.blockface.careers.config.ConfigPath.*;

/**
 * @author dumptruckman, SwearWord
 */
public class Config {

    private static Plugin plugin;
    private static CommentedConfiguration config;
    private static HashSet<String> SWITCHABLES;
    private static HashSet<String> USABLES;

    /**
     * Loads the configuration data into memory and sets defaults
     * @throws java.io.IOException
     */
    public static void load() throws IOException {
        Config.plugin = Careers.getInstance();

        // Make the data folders
        plugin.getDataFolder().mkdirs();

        // Check if the config file exists.  If not, create it.
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        // Load the configuration file into memory
        config = new CommentedConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        config.load();

        // Sets defaults config values
        setDefaults();

        // Saves the configuration from memory to file
        config.save();
    }

    /**
     * Loads default settings for any missing config values
     */
    private static void setDefaults() {
        for (ConfigPath path : ConfigPath.values()) {
            config.addComment(path.getPath(), path.getComments());
            if (config.getString(path.getPath()) == null) {
                config.setProperty(path.getPath(), path.getDefault());
            }
        }
    }

    private static HashSet<String> createHashSet(String line) {
        HashSet<String> ret = new HashSet<String>();
        Collections.addAll(ret, line.split(","));
        return ret;
    }

    private static Boolean getBoolean(ConfigPath path) {
        return config.getBoolean(path.getPath(), (Boolean)path.getDefault());
    }

    private static Integer getInt(ConfigPath path) {
        return config.getInt(path.getPath(), (Integer)path.getDefault());
    }

    private static String getString(ConfigPath path) {
        return config.getString(path.getPath(), (String)path.getDefault());
    }

    /**
     * Retrieves the language file name for this module
     * @return Language file name
     */
    public static String getLanguageFileName() {
        return config.getString(LANGUAGE.getPath());
    }

    public static Boolean isDebugging() {
        return getBoolean(ISDEBUGGING);
    }
}
