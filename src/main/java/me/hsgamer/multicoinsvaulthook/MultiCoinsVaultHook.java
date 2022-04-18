package me.hsgamer.multicoinsvaulthook;

import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.core.bukkit.baseplugin.BasePlugin;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.multicoinsvaulthook.command.BalanceCommand;
import me.hsgamer.multicoinsvaulthook.command.PayCommand;
import me.hsgamer.multicoinsvaulthook.config.MainConfig;
import me.hsgamer.multicoinsvaulthook.hook.VaultEconomyHook;
import me.hsgamer.multicoinsvaulthook.wrapper.CoinHolderWrapper;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public final class MultiCoinsVaultHook extends BasePlugin {
    public final MainConfig mainConfig = new MainConfig(this);
    private CoinHolderWrapper coinHolderWrapper = null;

    @Override
    public void load() {
        mainConfig.setup();
        registerProvider(Economy.class, new VaultEconomyHook(this));
    }

    @Override
    public void enable() {
        Permissions.register();
        registerCommand(new PayCommand(this));
        registerCommand(new BalanceCommand(this));
    }

    @Override
    public void disable() {
        Permissions.unregister();
    }

    public CoinHolderWrapper getCoinHolderWrapper() {
        if (coinHolderWrapper == null) {
            MultiCoins multiCoins = JavaPlugin.getPlugin(MultiCoins.class);
            Optional<CoinHolder> optional = multiCoins.getCoinManager().getHolder(MainConfig.MAIN_CURRENCY.getValue());
            if (optional.isEmpty()) {
                throw new IllegalStateException("Cannot find the main currency");
            }
            CoinHolder coinHolder = optional.get();
            CoinFormatter coinFormatter = multiCoins.getCoinManager().getFormatter(MainConfig.MAIN_CURRENCY.getValue());
            coinHolderWrapper = new CoinHolderWrapper(coinHolder, coinFormatter);
        }
        return coinHolderWrapper;
    }
}
