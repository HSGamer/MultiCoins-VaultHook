package me.hsgamer.multicoinsvaulthook.config;

import me.hsgamer.multicoins.core.bukkit.config.BukkitConfig;
import me.hsgamer.multicoins.core.config.PathableConfig;
import me.hsgamer.multicoins.core.config.path.impl.StringConfigPath;
import org.bukkit.plugin.Plugin;

public class MainConfig extends PathableConfig {
    public static final StringConfigPath MAIN_CURRENCY = new StringConfigPath("main-currency", "money");
    public static final StringConfigPath GIVE_SELF_ERROR = new StringConfigPath("give-self-error", "&cYou can't give yourself money!");

    public MainConfig(Plugin plugin) {
        super(new BukkitConfig(plugin, "config.yml"));
    }
}
