package me.hsgamer.multicoinsvaulthook;

import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.core.bukkit.baseplugin.BasePlugin;
import me.hsgamer.multicoins.core.bukkit.config.BukkitConfig;
import me.hsgamer.multicoins.core.config.proxy.ConfigGenerator;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.multicoinsvaulthook.command.BalanceCommand;
import me.hsgamer.multicoinsvaulthook.command.PayCommand;
import me.hsgamer.multicoinsvaulthook.config.MainConfig;
import me.hsgamer.multicoinsvaulthook.hook.VaultEconomyHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class MultiCoinsVaultHook extends BasePlugin {
    public final MainConfig mainConfig = ConfigGenerator.newInstance(MainConfig.class, new BukkitConfig(this, "config.yml"));
    private MultiCoins multiCoins = null;
    private CoinHolder coinHolder = null;

    @Override
    public void load() {
        registerProvider(Economy.class, new VaultEconomyHook(this));
    }

    @Override
    public void enable() {
        registerCommand(new PayCommand(this));
        registerCommand(new BalanceCommand(this));
    }

    @Override
    protected List<Class<?>> getPermissionClasses() {
        return Collections.singletonList(Permissions.class);
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public CoinHolder getCoinHolder() {
        if (coinHolder == null) {
            MultiCoins multiCoins = JavaPlugin.getPlugin(MultiCoins.class);
            Optional<CoinHolder> optional = multiCoins.getCoinManager().getHolder(mainConfig.getMainCurrency());
            if (optional.isEmpty()) {
                throw new IllegalStateException("Cannot find the main currency");
            }
            coinHolder = optional.get();
        }
        return coinHolder;
    }

    public MultiCoins getMultiCoins() {
        if (multiCoins == null) {
            multiCoins = JavaPlugin.getPlugin(MultiCoins.class);
        }
        return multiCoins;
    }
}
