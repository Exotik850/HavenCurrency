package dev.byt3.haven.currency.commands;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;

public class CurrencyCommands {
    public static void setupCommands(JavaPlugin plugin) {
        plugin.getCommandRegistry().registerCommand(new AddBalanceCommand());
        plugin.getCommandRegistry().registerCommand(new BalanceCommand());
        plugin.getCommandRegistry().registerCommand(new SetBalanceCommand());
    }
}
