package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;

public class MetricSystemContext {

    private static final ThreadLocal<MetricSystem> METRIC_SYSTEM_THREAD_LOCAL = ThreadLocal.withInitial( () -> {
        return MetricSystem.SI;
    });

    public static void set(MetricSystem system) {
        METRIC_SYSTEM_THREAD_LOCAL.set(system);
    }

    public static MetricSystem get() {
        return METRIC_SYSTEM_THREAD_LOCAL.get();
    }

    public static void clear() {
        METRIC_SYSTEM_THREAD_LOCAL.remove();
    }


}
