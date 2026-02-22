package dev.byt3.haven.currency;

import com.hypixel.hytale.server.core.permissions.PermissionsModule;

import java.util.Set;

public class CurrencyPermissions {

    public static void setPermissions() {
        PermissionsModule permissions = PermissionsModule.get();
        permissions.addGroupPermission("OP", Set.of(
                "haven.currency.addbalance",
                "haven.currency.setbalance"
        ));
        permissions.addGroupPermission("Default", Set.of("haven.currency.balance"));
    }
}
