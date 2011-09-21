package org.blockface.careers;

import org.blockface.bukkitstats.CallHome;
import org.blockface.careers.commands.Info;
import org.blockface.careers.commands.SetJob;
import org.blockface.careers.config.Config;
import org.blockface.careers.events.PlayerEvents;
import org.blockface.careers.locale.Language;
import org.blockface.careers.locale.Logging;
import org.blockface.careers.persistance.PersistanceManager;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Careers extends JavaPlugin {

    private static Careers plugin;

    public void onDisable() {
        PersistanceManager.unload();
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
        plugin = this;

        //Call Home
        CallHome.load(this);

        try {
            //Load Config
            Config.load();
            //Load Language
            Language.load();
            //Load Persistance
            PersistanceManager.load();
            Logging.load(this);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        setCommands();
        registerEvents();

        System.out.println(this + " is now enabled!");
    }

    private void setCommands() {
        getCommand("setjob").setExecutor(new SetJob());
        getCommand("jobinfo").setExecutor(new Info());
    }

    private void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        PlayerEvents pe = new PlayerEvents();
        pm.registerEvent(Event.Type.PLAYER_JOIN,pe, Event.Priority.Normal,this);
        pm.registerEvent(Event.Type.PLAYER_CHAT,pe, Event.Priority.Normal,this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT,pe, Event.Priority.Highest,this);
    }

    public static Careers getInstance() {
        return plugin;
    }


}
