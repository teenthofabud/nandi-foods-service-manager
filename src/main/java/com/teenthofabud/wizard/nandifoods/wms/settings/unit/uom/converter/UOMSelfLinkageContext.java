package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

public class UOMSelfLinkageContext {

    private static final ThreadLocal<Boolean> REVERSE = ThreadLocal.withInitial( () -> {
        return Boolean.FALSE;
    });

    public static void setCascadeLevelContext(Boolean reverse) {
        REVERSE.set(reverse);
    }

    public static Boolean getCascadeLevelContext() {
        return REVERSE.get();
    }

    public static void clearCascadeLevelContext() {
        REVERSE.remove();
    }


}
