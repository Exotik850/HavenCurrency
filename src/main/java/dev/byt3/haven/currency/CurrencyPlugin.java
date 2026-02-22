package dev.byt3.haven.currency;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.modules.entity.EntityRegistry;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.byt3.haven.currency.commands.AddBalanceCommand;
import dev.byt3.haven.currency.commands.BalanceCommand;
import dev.byt3.haven.currency.commands.CurrencyCommands;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class CurrencyPlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.get("Currency");
    public static int CURRENCY = Integer.MAX_VALUE;

    public CurrencyPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    public static int getCurrencyId() {
        if (CURRENCY == Integer.MAX_VALUE) {
            throw new IllegalStateException("Currency plugin has not been loaded yet!");
        }
        return CURRENCY;
    }

    public static void addBalance(Ref<EntityStore> ref, ComponentAccessor<EntityStore> store, float amount) {
        EntityStatMap statMap = store.getComponent(ref, EntityStatMap.getComponentType());
        if (statMap == null) {
            statMap = new EntityStatMap();
            store.addComponent(ref, EntityStatMap.getComponentType(), statMap);
        }
        statMap.addStatValue(CURRENCY, amount);
    }

    public static void setBalance(Ref<EntityStore> ref, ComponentAccessor<EntityStore> store, float amount) {
        float current = getBalance(ref, store);
        addBalance(ref, store, amount - current);
    }

    public static float getBalance(Ref<EntityStore> ref, ComponentAccessor<EntityStore> store) {
        EntityStatMap statMap = store.getComponent(ref, EntityStatMap.getComponentType());
        if (statMap == null) return 0;
        EntityStatValue currency = statMap.get(CURRENCY);
        if (currency == null) return 0;
        return currency.get();
    }
    public static float getBalance(Ref<EntityStore> ref) {
        return getBalance(ref, ref.getStore());
    }

    @Override
    public void setup() {
        CurrencyCommands.setupCommands(this);
        CurrencyPermissions.setPermissions();
        LOGGER.atInfo().log("Currency plugin has been loaded!");
    }

    @Override
    public void start() {
        CURRENCY = EntityStatType.getAssetMap().getIndex("Currency");
    }
}
