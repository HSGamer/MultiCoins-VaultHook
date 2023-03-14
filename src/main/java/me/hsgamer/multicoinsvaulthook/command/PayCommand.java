package me.hsgamer.multicoinsvaulthook.command;

import me.hsgamer.multicoins.core.bukkit.utils.MessageUtils;
import me.hsgamer.multicoins.core.common.Validate;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.multicoinsvaulthook.MultiCoinsVaultHook;
import me.hsgamer.multicoinsvaulthook.Permissions;
import me.hsgamer.multicoinsvaulthook.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PayCommand extends Command {
    private final MultiCoinsVaultHook instance;

    public PayCommand(MultiCoinsVaultHook instance) {
        super("pay", "Transfer money to the player", "/pay <player> <amount>", Collections.emptyList());
        this.instance = instance;
        setPermission(Permissions.PAY.getName());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!testPermission(sender)) {
            return false;
        }
        if (args.length < 2) {
            MessageUtils.sendMessage(sender, getUsage());
            return false;
        }
        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, instance.getMultiCoins().getMessageConfig().getPlayerOnly());
            return false;
        }

        CoinHolder coinHolder = instance.getCoinHolder();

        Player player = (Player) sender;
        OfflinePlayer receiver = Utils.getOfflinePlayer(args[0]);
        if (receiver == player) {
            MessageUtils.sendMessage(sender, instance.getMainConfig().getGiveSelfError());
            return false;
        }
        UUID playerUUID = player.getUniqueId();
        UUID receiverUUID = receiver.getUniqueId();

        Optional<Double> optionalAmount = Validate.getNumber(args[1])
                .map(BigDecimal::doubleValue)
                .filter(value -> value > 0)
                .filter(value -> coinHolder.getBalance(playerUUID) >= value);
        if (optionalAmount.isEmpty()) {
            MessageUtils.sendMessage(sender, instance.getMultiCoins().getMessageConfig().getInvalidNumber());
            return false;
        }
        double amount = optionalAmount.get();

        coinHolder.takeBalance(playerUUID, amount);
        coinHolder.giveBalance(receiverUUID, amount);
        MessageUtils.sendMessage(sender, coinHolder.getCoinFormatter().replace(
                instance.getMainConfig().getPaySuccess(), receiverUUID, amount
        ));
        MessageUtils.sendMessage(receiverUUID, coinHolder.getCoinFormatter().replace(
                instance.getMainConfig().getPayReceived(), playerUUID, amount
        ));
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
