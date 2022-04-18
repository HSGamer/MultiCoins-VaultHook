package me.hsgamer.multicoinsvaulthook.command;

import me.hsgamer.hscore.common.Validate;
import me.hsgamer.multicoins.config.MessageConfig;
import me.hsgamer.multicoins.core.bukkit.utils.MessageUtils;
import me.hsgamer.multicoinsvaulthook.MultiCoinsVaultHook;
import me.hsgamer.multicoinsvaulthook.Permissions;
import me.hsgamer.multicoinsvaulthook.Utils;
import me.hsgamer.multicoinsvaulthook.config.MainConfig;
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
            MessageUtils.sendMessage(sender, MessageConfig.PLAYER_ONLY.getValue());
            return false;
        }

        Player player = (Player) sender;
        OfflinePlayer receiver = Utils.getOfflinePlayer(args[0]);
        if (receiver == player) {
            MessageUtils.sendMessage(sender, MainConfig.GIVE_SELF_ERROR.getValue());
            return false;
        }
        UUID playerUUID = player.getUniqueId();
        UUID receiverUUID = receiver.getUniqueId();

        Optional<Double> optionalAmount = Validate.getNumber(args[1])
                .map(BigDecimal::doubleValue)
                .filter(value -> value > 0)
                .filter(value -> instance.getCoinHolderWrapper().getHolder().getOrCreateEntry(playerUUID).getBalance() >= value);
        if (optionalAmount.isEmpty()) {
            MessageUtils.sendMessage(sender, MessageConfig.INVALID_NUMBER.getValue());
            return false;
        }
        double amount = optionalAmount.get();

        instance.getCoinHolderWrapper().getHolder().getOrCreateEntry(playerUUID).takeBalance(amount);
        instance.getCoinHolderWrapper().getHolder().getOrCreateEntry(receiverUUID).giveBalance(amount);
        MessageUtils.sendMessage(sender, instance.getCoinHolderWrapper().getFormatter().replace(
                MessageConfig.GIVE_SUCCESS.getValue(), receiverUUID, amount
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
