package org.blockface.careers.managers;

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;
import org.blockface.careers.config.Config;
import org.blockface.careers.locale.Language;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EconomyManager {

    private static Method method;

    public static void load(Plugin plugin) {
        Methods.setMethod(plugin.getServer().getPluginManager());
        method = Methods.getMethod();
    }

    public static Method getMethod() {
        return method;
    }

    public static boolean payWage(Player player, double amount) {
        Method.MethodAccount source = method.getAccount(Config.getSourceAccount());
        Method.MethodAccount target = method.getAccount(player.getName());
        if(!source.hasEnough(amount)) {
            Language.NO_MONEY.bad(player);
            return false;}
        source.subtract(amount);
        target.add(amount);
        Language.RECEIVED_MONEY.good(player,method.format(amount));
        return true;
    }

    public static void payAll(Player src, Player trg) {

        Method.MethodAccount source = method.getAccount(src.getName());
        Method.MethodAccount target = method.getAccount(trg.getName());
        double amount = source.balance();
        source.subtract(amount);
        target.add(amount);
        Language.RECEIVED_MONEY.good(trg,method.format(amount));
    }

    public static boolean pay(Player src, Player trg, double amount, String reason) {

        Method.MethodAccount source = method.getAccount(src.getName());
        Method.MethodAccount target = method.getAccount(trg.getName());
        if(!source.hasEnough(amount)) {
            Language.CANNOT_AFFORD.bad(src,method.format(amount-source.balance()),reason);
            return false;
        }
        source.subtract(amount);
        target.add(amount);
        Language.RECEIVED_MONEY.good(trg,method.format(amount));
        Language.SENT_MONEY.good(src,method.format(amount));
        return true;
    }

    public static void pocketpicked(Player thief, Player mark){
    	
    	Method.MethodAccount source = method.getAccount(thief.getName());
        Method.MethodAccount target = method.getAccount(mark.getName());
        double amount = Math.random() * 0.25 * target.balance();
        target.subtract(amount);
        source.add(amount);
        Language.RECEIVED_MONEY.good(thief,method.format(amount));
    }
    
}