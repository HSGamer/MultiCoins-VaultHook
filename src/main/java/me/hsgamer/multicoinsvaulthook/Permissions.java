package me.hsgamer.multicoinsvaulthook;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public final class Permissions {
    public static final Permission BALANCE = new Permission("multicoins.balance", PermissionDefault.TRUE);
    public static final Permission BALANCE_OTHERS = new Permission("multicoins.balance.others", PermissionDefault.TRUE);
    public static final Permission BALANCE_TOP = new Permission("multicoins.balancetop", PermissionDefault.TRUE);
    public static final Permission PAY = new Permission("multicoins.pay", PermissionDefault.TRUE);

    public static void register() {
        Bukkit.getPluginManager().addPermission(BALANCE);
        Bukkit.getPluginManager().addPermission(BALANCE_OTHERS);
        Bukkit.getPluginManager().addPermission(BALANCE_TOP);
        Bukkit.getPluginManager().addPermission(PAY);
    }

    public static void unregister() {
        Bukkit.getPluginManager().removePermission(BALANCE);
        Bukkit.getPluginManager().removePermission(BALANCE_OTHERS);
        Bukkit.getPluginManager().removePermission(BALANCE_TOP);
        Bukkit.getPluginManager().removePermission(PAY);
    }
}
