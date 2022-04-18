package me.hsgamer.multicoinsvaulthook;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Utils {
    @SuppressWarnings("deprecation")
    public static OfflinePlayer getOfflinePlayer(String name) {
        return Bukkit.getOfflinePlayer(name);
    }
}
