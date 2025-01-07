package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;

public class MetricSystemContext {

    private static final ThreadLocal<MeasurementSystem> METRIC_SYSTEM_THREAD_LOCAL = ThreadLocal.withInitial( () -> {
        return MeasurementSystem.SI;
    });

    public static void set(MeasurementSystem system) {
        METRIC_SYSTEM_THREAD_LOCAL.set(system);
    }

    public static MeasurementSystem get() {
        return METRIC_SYSTEM_THREAD_LOCAL.get();
    }

    public static void clear() {
        METRIC_SYSTEM_THREAD_LOCAL.remove();
    }


}
