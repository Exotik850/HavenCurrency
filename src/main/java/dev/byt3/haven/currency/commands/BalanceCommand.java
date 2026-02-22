package dev.byt3.haven.currency.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.byt3.haven.currency.CurrencyPlugin;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.*;
import java.util.Set;
import java.util.UUID;

public class BalanceCommand extends AbstractPlayerCommand {
    private final OptionalArg<String> playerNameArg = this.withOptionalArg("player", "The player to check the balance of", ArgTypes.STRING);

    public BalanceCommand() {
        super("balance", "Check your balance or the balance of another player");
        this.addAliases("bal", "money");
        this.setPermissionGroup(GameMode.Adventure);
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        String playerName = this.playerNameArg.get(commandContext);
        if (playerName == null || playerName.equals(playerRef.getUsername())) {
            float balance = CurrencyPlugin.getBalance(ref, store);
            commandContext.sendMessage(Message.raw("Your balance is %.2f".formatted(balance)).color(Color.green));
            return;
        }
        UUID playerUuid = playerRef.getUuid();
        boolean isOp = Boolean.TRUE.equals(PermissionsModule.hasPermission(Set.of(playerUuid.toString()), "OP"));
        if (!isOp) {
            commandContext.sendMessage(Message.raw("You do not have permission to check other players' balances").color(Color.red));
            return;
        }
        Ref<EntityStore> targetRef = Universe.get().getPlayer(playerName, NameMatching.STARTS_WITH_IGNORE_CASE).getReference();
        if (targetRef == null) {
            commandContext.sendMessage(Message.raw("Player %s not found".formatted(playerName)).color(Color.red));
            return;
        }
        float balance = CurrencyPlugin.getBalance(targetRef, store);
        commandContext.sendMessage(Message.raw("%s's balance is %.2f".formatted(playerName, balance)).color(Color.green));

    }
}
