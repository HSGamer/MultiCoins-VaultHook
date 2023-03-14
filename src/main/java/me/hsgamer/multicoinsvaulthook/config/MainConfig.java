package me.hsgamer.multicoinsvaulthook.config;

import me.hsgamer.multicoins.core.config.annotation.ConfigPath;

public interface MainConfig {
    @ConfigPath("main-currency")
    default String getMainCurrency() {
        return "money";
    }

    @ConfigPath("give-self-error")
    default String getGiveSelfError() {
        return "&cYou can't give yourself money!";
    }

    @ConfigPath("pay-success")
    default String getPaySuccess() {
        return "&aYou have paid &e{amount} {currency} &ato &e{name}";
    }

    @ConfigPath("pay-received")
    default String getPayReceived() {
        return "&aYou have received &e{amount} {currency} &afrom &e{name}";
    }
}
