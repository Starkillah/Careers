package org.blockface.careers.locale;

import org.blockface.careers.Careers;
import org.blockface.careers.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dumptruckman, SwearWord
 */
public enum Language {
    NO_COMMAND_PERMISSION ("command.no_permission", "You do not have permission to access this command!", 0),
    IN_GAME_ONLY ("misc.in_game_only", "Only in game players may use this feature!", 0),
    ERROR ("error", "[Careers]", 0),
    SUCCESS ("success", "[Careers]", 0),
    HELP ("help", "[Help]", 0),

    LEVELUP("notifications.levelup","You have are now at level %s!",0),
    THEFT_FAILED("notifications.theftfailed","You've failed to pick the lock.",0),
    THEFT_SUCCEEDED("notifications.thiefsucceeded","You managed to pick the lock.",0),
    CRITICAL_HIT("notifications.criticalhit","Critical hit!",0),
    EXP_ADDED("notifications.expadded","%s points added.",0),
    WANTED("notification.wanted","%s is wanted for %s",0),
    WITNESSED("notification.witnessed","You just saw %s commit %s.",0),
    SAW_YOU("notification.sawyou","%s just saw you commit %s.",0),
    TELEPORT("notification.teleport","You cannot teleport right now.",0),
    REPORTED("notification.reported","You have reported %s to the police.",0),
    DODGED("notification.dodged","You haved dodged the attack!",0),
    WAS_DODGED("notification.wasdodged","Your attack was dodged!",0),
    ARRESTED("notification.arrested","%s was arrested for %s",0),
    FREED("notification.freed","You were released from jail.",0),
    JAIL_TIME("notification.jailtime","Jail time left: %s",0),
    SET_JAIL("notification.setjail","Jail set.",0),
    NO_MONEY("notification.nomoney","Government is bankrupt, sorry.",0),
    RECEIVED_MONEY("notification.receivedmoney","You were paid %s",0),
    SENT_MONEY("notification.sentmoney","You paid %s",0),
    CRIMINAL_ESCAPED("notification.escape","%s escaped with his crimes.",0),
    POISONED("notification.poisoned","You have been poisoned, find a doctor quick!",0),
    POISONED_SUCCESS("notification.poisonedsuccess","You poisoned %s.",0),
    POISONED_FAST("notification.poison.fast","You poisoned %s severely.",0),
    POISONED_DEATH("notification.poison.death","%s has died from poisoning",0),
    POISONED_CURED("notification.poison.cured","You have been cured of poison.",0),
    NIGHT_ONLY("notification.nightonly","You may only kill at night.",0),
    IN_HELL("notification.inhell","You are stuck in hell for %s seconds." ,0),
    CANNOT_AFFORD("notification.cannotafford","You need an additional %s for %s" ,0),
    PICKPOCKETED("notification.pickPocketed","You managed to pickpocket %s" ,0),
    FAILED_PICKPOCKET("notification.failedPickPocket","You've been caught trying to pickpocket!" ,0)


    ;

    private static List<String> deprecatedPaths = Arrays.asList(
            // Put old unused paths in here to be removed from the language file
    );


    private String path;
    private List<String> def;
    private int build;
    private static int BUILD;
    private static Configuration language;

    Language(String path, String def, int lastChangedBuild) {
        this.path = path;
        this.def = Arrays.asList(def);
        this.build = lastChangedBuild;
    }

    Language(String path, List<String> def, int lastChangedBuild) {
        this.path = path;
        this.def = def;
        this.build = lastChangedBuild;
    }

    /**
     * Retrieves the path for a config option
     *
     * @return The path for a config option
     */
    public String getPath() {
        return path;
    }

    /**
     * Retrieves the default value for a config path
     *
     * @return The default value for a config path
     */
    public List<String> getDefault() {
        return def;
    }

    /**
     * Retrieves the build number that this language was last changed on
     *
     * @return The build number this language was last changed on
     */
    public int getBuild() {
        return build;
    }

    /**
     * Loads the language data into memory and sets defaults
     *
     * @throws java.io.IOException
     */
    public static void load() throws IOException {

        // Make the data folders
        Careers.getInstance().getDataFolder().mkdirs();

        // Check if the language file exists.  If not, create it.
        File languageFile = new File(Careers.getInstance().getDataFolder(), Config.getLanguageFileName());
        if (!languageFile.exists()) {
            languageFile.createNewFile();
        }

        // Load the language file into memory
        language = new Configuration(languageFile);
        language.load();

        BUILD = language.getInt("last_run_build", 0);

        // Sets defaults language values
        setDefaults();
        removeDeprecatedLanguage();

        language.setProperty("last_run_build", Careers.getInstance().getDescription().getVersion());

        // Saves the configuration from memory to file
        language.save();
    }

    /**
     * Loads default settings for any missing language strings
     */
    private static void setDefaults() {
        for (Language path : Language.values()) {
            if (language.getString(path.getPath()) == null || path.getBuild() > BUILD) {
                language.setProperty(path.getPath(), path.getDefault());
            }
        }
    }

    private static void removeDeprecatedLanguage() {
        for (String path : deprecatedPaths) {
            language.removeProperty(path);
        }
    }

    private static String formatString(String string, Object...args) {
        // Replaces & with the Section character
        string = string.replaceAll("&", Character.toString((char)167));
        // If there are arguments, %n notations in the message will be
        // replaced
        if (args != null) {
            string = String.format(string,args);
        }
        return string;
    }

    /**
     * Gets a list of the messages for a given path.  Color codes will be
     * converted and any lines too long will be split into an extra element in
     * the list.  %n notated variables n the message will be replaced with the
     * optional arguments passed in.
     *
     * @param path Path of the message in the language yaml file.
     * @param args Optional arguments to replace %n variable notations
     * @return A List of formatted Strings
     */
    public static List<String> getStrings(Language path, Object...args) {
        // Gets the messages for the path submitted
        List<Object> list = language.getList(path.getPath());

        List<String> message = new ArrayList<String>();
        if (list == null) {
            Logging.warning("Missing language for: " + path.getPath());
            return message;
        }
        // Parse each item in list
        for (int i = 0; i < list.size(); i++) {
            String temp = formatString(list.get(i).toString(), args);

            // Pass the line into the line breaker
            List<String> lines = Font.splitString(temp);
            // Add the broken up lines into the final message list to return
            for (int j = 0; j < lines.size(); j++) {
                message.add(lines.get(j));
            }
        }
        return message;
    }

    public static String getString(Language language, Object...args) {
        List<Object> list = Language.language.getList(language.getPath());
        if (list == null) {
            Logging.warning("Missing language for: " + language.getPath());
            return "";
        }
        if (list.isEmpty()) return "";
        return (formatString(list.get(0).toString(), args));
    }

    public String getString(Object...args) {
        return getString(this, args);
    }

    public void bad(CommandSender sender, Object... args) {
        send(ChatColor.RED.toString() + Language.ERROR.getString(), sender, args);
    }

    public void normal(CommandSender sender, Object... args) {
        send("", sender, args);
    }

    private void send(String prefix, CommandSender sender, Object... args) {
        List<String> messages = getStrings(this, args);
        for (int i = 0; i < messages.size(); i++) {
            if (i == 0) {
                sender.sendMessage(prefix + " " + messages.get(i));
            } else {
                sender.sendMessage(messages.get(i));
            }
        }
    }

    public void broadcastBad(Object... args) {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            send(ChatColor.RED + Language.ERROR.getString(),player,args);
        }
    }

    public void broadcastGood(Object... args) {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            send(ChatColor.GREEN + Language.SUCCESS.getString(),player,args);
        }
    }

    public void good(CommandSender sender, Object... args) {
        send(ChatColor.GREEN.toString() + Language.SUCCESS.getString(), sender, args);
    }

    public void good(String sender, Object... args) {
        Player player = Bukkit.getServer().matchPlayer(sender).get(0);
        if(player==null) return;
        good(player,args);
    }
    public void bad(String sender, Object... args) {
        Player player = Bukkit.getServer().matchPlayer(sender).get(0);
        if(player==null) return;
        bad(player,args);
    }

    public void help(CommandSender sender, Object... args) {
        send(ChatColor.YELLOW.toString() + Language.HELP.getString(), sender, args);
    }

    /**
     * Sends a custom string to a player.
     *
     * @param player
     * @param message
     * @param args
     */
    public static void sendMessage(CommandSender player, String message, Object...args) {
        List<String> messages = Font.splitString(formatString(message, args));
        for (String s : messages) {
            player.sendMessage(s);
        }
    }
}