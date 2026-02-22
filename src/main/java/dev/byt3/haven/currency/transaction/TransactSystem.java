package dev.byt3.haven.currency.transaction;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.entity.LivingEntity;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import dev.byt3.haven.currency.CurrencyPlugin;

import javax.annotation.Nonnull;

public class TransactSystem extends EntityEventSystem<EntityStore, TransactEvent> {
    private static final HytaleLogger LOGGER = HytaleLogger.get("TransactSystem");

    protected TransactSystem(@Nonnull Class<TransactEvent> eventType) {
        super(eventType);
    }

    @Override
    public void handle(int i, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull TransactEvent transactEvent) {
        LivingEntity player = archetypeChunk.getComponent(i, Player.getComponentType());
        if (player == null) {
            player = archetypeChunk.getComponent(i, NPCEntity.getComponentType());
        }
        if (player == null) return;
        Ref<EntityStore> source = transactEvent.getSource();
        if (source != null) {
            EntityStatMap statMap = store.getComponent(source, EntityStatMap.getComponentType());
            if (statMap == null) return;
            EntityStatValue sourceCurrency = statMap.get(CurrencyPlugin.getCurrencyId());
            if (sourceCurrency == null) return;
            if (sourceCurrency.get() < transactEvent.getAmount()) {
                LOGGER.atWarning().log("Entity %s attempted to transact %d currency, but only has %d", source.getIndex(), transactEvent.getAmount(), sourceCurrency.get());
            };
            statMap.addStatValue(CurrencyPlugin.getCurrencyId(), -transactEvent.getAmount());
        }
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);
        EntityStatMap statMap = store.getComponent(ref, EntityStatMap.getComponentType());
        if (statMap == null) return;
        statMap.addStatValue(CurrencyPlugin.getCurrencyId(), transactEvent.getAmount());
        LOGGER.atInfo().log("Entity %s transacted %d currency", ref.getIndex(), transactEvent.getAmount());
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(Query.or(Player.getComponentType(), NPCEntity.getComponentType()), EntityStatMap.getComponentType());
    }
}
