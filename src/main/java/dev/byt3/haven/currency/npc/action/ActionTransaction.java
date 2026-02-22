package dev.byt3.haven.currency.npc.action;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import dev.byt3.haven.currency.CurrencyPlugin;
import dev.byt3.haven.currency.npc.EntityProvider;
import dev.byt3.haven.currency.transaction.TransactEvent;

import javax.annotation.Nonnull;

public class ActionTransaction extends ActionBase {
    private final float amount;

    public ActionTransaction(@Nonnull BuilderActionTransaction builderActionBase, BuilderSupport support) {
        super(builderActionBase);
        this.amount = builderActionBase.getAmount(support);
    }

    @Override
    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        if (!(sensorInfo instanceof EntityProvider entityProvider)) {
            return false;
        }
        Ref<EntityStore> target = entityProvider.getEntity();
        if (target == null) return false;
        TransactEvent event = new TransactEvent(amount, ref);
        store.invoke(target, event);
        return event.isSuccess();
    }
}
