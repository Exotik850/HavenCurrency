package dev.byt3.haven.currency.transaction;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.system.CancellableEcsEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TransactEvent extends CancellableEcsEvent {

    private float amount;
    @Nullable
    private Ref<EntityStore> source;
    private boolean success = false;

    public TransactEvent(float amount) {
        this.amount = amount;
        this.source = null;
    }

    public TransactEvent(float amount, @Nonnull Ref<EntityStore> source) {
        this.amount = amount;
        this.source = source;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Nullable
    public Ref<EntityStore> getSource() {
        return source;
    }

    public void setSource(@Nullable Ref<EntityStore> source) {
        this.source = source;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
