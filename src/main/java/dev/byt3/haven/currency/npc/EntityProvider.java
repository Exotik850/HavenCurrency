package dev.byt3.haven.currency.npc;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.sensorinfo.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityProvider extends PositionProvider {
    @Nullable
    private Ref<EntityStore> entity = null;

    @Nullable
    @Override
    public Ref<EntityStore> setTarget(@Nullable Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
        this.entity = ref;
        return super.setTarget(ref, componentAccessor);
    }

    @Nullable
    public Ref<EntityStore> getEntity() {
        return entity;
    }
}
