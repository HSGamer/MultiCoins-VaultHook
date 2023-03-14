package me.hsgamer.multicoinsvaulthook;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public final class Permissions {
    public static final Permission BALANCE = new Permission("multicoins.balance", PermissionDefault.TRUE);
    public static final Permission BALANCE_OTHERS = new Permission("multicoins.balance.others", PermissionDefault.TRUE);
    public static final Permission PAY = new Permission("multicoins.pay", PermissionDefault.TRUE);

    private Permissions() {
        // EMPTY
    }
}
