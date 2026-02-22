package dev.byt3.haven.currency.npc.sensor;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
import com.hypixel.hytale.server.npc.instructions.Sensor;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import dev.byt3.haven.currency.CurrencyPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SensorBalance extends SensorBase {

    private float amount;
    private BuilderSensorBalance.BalanceComparisonType comparisonType;

    public SensorBalance(BuilderSensorBalance builder, BuilderSupport support) {
        super(builder);
        this.amount = builder.getAmount(support);
        this.comparisonType = builder.getComparisonType(support);
    }

    @Override
    public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
        float balance = CurrencyPlugin.getBalance(ref, store);
        return switch (comparisonType) {
            case GT -> balance > amount;
            case EQ -> balance == amount;
            case GTE -> balance >= amount;
            case LT -> balance < amount;
            case LTE -> balance <= amount;
        };
    }

    @Nullable
    @Override
    public InfoProvider getSensorInfo() {
        return null;
    }
}
