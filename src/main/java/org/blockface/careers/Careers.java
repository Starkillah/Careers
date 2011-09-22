package org.blockface.careers;

import org.blockface.bukkitstats.CallHome;
import org.blockface.careers.commands.AddExp;
import org.blockface.careers.commands.Info;
import org.blockface.careers.commands.SetJail;
import org.blockface.careers.commands.SetJob;
import org.blockface.careers.config.Config;
import org.blockface.careers.events.EntityEvents;
import org.blockface.careers.events.PlayerEvents;
import org.blockface.careers.locale.Language;
import org.blockface.careers.locale.Logging;
import org.blockface.careers.managers.JailManager;
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

            //Load Jail
            JailManager.load();

            //Load Logger
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
        getCommand("addexp").setExecutor(new AddExp());
        getCommand("setjail").setExecutor(new SetJail());
    }

    private void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();

        //Player Events
        PlayerEvents pe = new PlayerEvents();
        pm.registerEvent(Event.Type.PLAYER_JOIN,pe, Event.Priority.Normal,this);
        pm.registerEvent(Event.Type.PLAYER_CHAT,pe, Event.Priority.Normal,this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT,pe, Event.Priority.Highest,this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY,pe, Event.Priority.Highest,this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT,pe, Event.Priority.Highest,this);

        //Entity Events
        EntityEvents ee = new EntityEvents();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE,ee, Event.Priority.Normal,this);
    }

    public static Careers getInstance() {
        return plugin;
    }


}
