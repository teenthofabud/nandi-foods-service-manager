package com.teenthofabud.wizard.nandifoods.wms.settings.unit.error;

import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSError;

public class UnitException extends WMSBaseException {

    public UnitException(WMSError error, Object[] parameters) {
        super(error, parameters);
    }

    @Override
    public String getDomain() {
        return "Settings";
    }

    @Override
    public String getSubDomain() {
        return "Unit";
    }
}
