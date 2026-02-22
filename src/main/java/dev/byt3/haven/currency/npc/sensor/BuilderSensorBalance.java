package dev.byt3.haven.currency.npc.sensor;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.Builder;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.EnumHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.FloatHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSingleValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
import com.hypixel.hytale.server.npc.instructions.Sensor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BuilderSensorBalance extends BuilderSensorBase {

    public BalanceComparisonType getComparisonType(BuilderSupport support) {
        return this.comparisonType.get(support.getExecutionContext());
    }

    public float getAmount(BuilderSupport support) {
        return this.amount.get(support.getExecutionContext());
    }

    public enum BalanceComparisonType implements Supplier<String> {
        GT,
        LT,
        EQ,
        GTE,
        LTE;

        @Override
        public String get() {
            return this.name();
        }
    }

    private final EnumHolder<BalanceComparisonType> comparisonType = new EnumHolder<>();
    private final FloatHolder amount = new FloatHolder();

    @Nullable
    @Override
    public String getShortDescription() {
        return "Balance Sensor";
    }

    @Nullable
    @Override
    public String getLongDescription() {
        return "A sensor that checks the balance of a player.";
    }

    @Override
    public Builder<Sensor> readConfig(JsonElement data) {
        this.getEnum(data, "ComparisonType", this.comparisonType, BalanceComparisonType.class, BalanceComparisonType.GT, BuilderDescriptorState.Experimental, "", "");
        this.getFloat(data, "Amount", this.amount, 0, DoubleSingleValidator.greater(0), BuilderDescriptorState.Experimental, "", "");
        return super.readConfig(data);
    }

    @Nullable
    @Override
    public SensorBalance build(BuilderSupport builderSupport) {
        return new SensorBalance(this, builderSupport);
    }

    @Nullable
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Experimental;
    }
}
