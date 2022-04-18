package me.hsgamer.multicoinsvaulthook.wrapper;

import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;

public class CoinHolderWrapper {
    private final CoinHolder holder;
    private final CoinFormatter formatter;

    public CoinHolderWrapper(CoinHolder holder, CoinFormatter formatter) {
        this.holder = holder;
        this.formatter = formatter;
    }

    public CoinHolder getHolder() {
        return holder;
    }

    public CoinFormatter getFormatter() {
        return formatter;
    }
}
