package me.hsgamer.multicoinsvaulthook.command;

import me.hsgamer.multicoins.config.MessageConfig;
import me.hsgamer.multicoins.core.bukkit.utils.MessageUtils;
import me.hsgamer.multicoinsvaulthook.MultiCoinsVaultHook;
import me.hsgamer.multicoinsvaulthook.Permissions;
import me.hsgamer.multicoinsvaulthook.Utils;
import me.hsgamer.multicoinsvaulthook.wrapper.CoinHolderWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BalanceCommand extends Command {
    private final MultiCoinsVaultHook instance;

    public BalanceCommand(MultiCoinsVaultHook instance) {
        super("balance", "Get the balance of a player", "/balance [player]", Collections.singletonList("bal"));
        this.instance = instance;
        setPermission(Permissions.BALANCE.getName());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
        if (!testPermission(sender)) {
            return false;
        }
        OfflinePlayer who;
        if (args.length > 0 && sender.hasPermission(Permissions.BALANCE_OTHERS)) {
            who = Utils.getOfflinePlayer(args[0]);
        } else if (sender instanceof Player) {
            who = (OfflinePlayer) sender;
        } else {
            MessageUtils.sendMessage(sender, MessageConfig.PLAYER_ONLY.getValue());
            return false;
        }
        UUID uuid = who.getUniqueId();
        CoinHolderWrapper coinHolderWrapper = instance.getCoinHolderWrapper();
        MessageUtils.sendMessage(sender, coinHolderWrapper.getFormatter().replace(MessageConfig.BALANCE.getValue(), uuid, coinHolderWrapper.getHolder().getOrCreateEntry(uuid).getBalance()));
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            String name = args[0];
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(s -> name.isBlank() || s.startsWith(name))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
