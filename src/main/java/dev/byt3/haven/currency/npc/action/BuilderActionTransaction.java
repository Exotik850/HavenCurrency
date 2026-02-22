package dev.byt3.haven.currency.npc.action;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.Builder;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.FloatHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSingleValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;

import javax.annotation.Nullable;

public class BuilderActionTransaction extends BuilderActionBase {

    private final FloatHolder amount = new FloatHolder();

    @Nullable
    @Override
    public String getShortDescription() {
        return "Enacts a currency transaction with the player.";
    }

    @Nullable
    @Override
    public String getLongDescription() {
        return "";
    }

    @Nullable
    @Override
    public ActionTransaction build(BuilderSupport builderSupport) {
        return null;
    }

    @Override
    public Builder<Action> readConfig(JsonElement data) {
        this.getFloat(data, "Amount", amount, 0f, null, BuilderDescriptorState.Experimental, "The amount of currency to transact.", "Positive values will give the player currency, while negative values will take currency from the player.");
        return super.readConfig(data);
    }

    @Nullable
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Experimental;
    }

    public float getAmount(BuilderSupport support) {
        return amount.get(support.getExecutionContext());
    }
}
