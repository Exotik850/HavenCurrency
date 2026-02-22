package dev.byt3.haven.currency.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.byt3.haven.currency.CurrencyPlugin;
import dev.byt3.haven.currency.MessageUtil;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class SetBalanceCommand extends AbstractPlayerCommand {

    private final RequiredArg<Float> amountArg = this.withRequiredArg("amount", "The amount to set the balance to", ArgTypes.FLOAT);
    private final OptionalArg<String> playerNameArg = this.withOptionalArg("player", "The player to set the balance for", ArgTypes.STRING);

    public SetBalanceCommand() {
        super("setbalance", "Set a player's balance");
        this.addAliases("setbal", "setmoney");
        this.requirePermission("haven.currency.setbalance");
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        String playerName = this.playerNameArg.get(commandContext);
        Float amount = this.amountArg.get(commandContext);
        if (amount == null) {
            MessageUtil.error(commandContext, "You must specify an amount to set");
            return;
        }
        if (playerName == null || playerName.equals("self") || playerName.equals(playerRef.getUsername())) {
            CurrencyPlugin.setBalance(ref, store, amount);
            MessageUtil.success(commandContext, "Set your balance to %.2f".formatted(amount));
            return;
        }
        Ref<EntityStore> targetRef = Universe.get().getPlayerByUsername(playerName, NameMatching.STARTS_WITH_IGNORE_CASE).getReference();
        if (targetRef == null) {
            MessageUtil.error(commandContext, "Player %s not found".formatted(playerName));
            return;
        }
        CurrencyPlugin.setBalance(targetRef, store, amount);
        MessageUtil.success(commandContext, "Set %s's balance to %.2f".formatted(playerName, amount));
    }
}

