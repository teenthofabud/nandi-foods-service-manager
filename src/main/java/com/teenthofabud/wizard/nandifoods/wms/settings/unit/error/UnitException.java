package com.teenthofabud.wizard.nandifoods.wms.settings.unit.error;

import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSError;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UnitException extends WMSBaseException {

    @ToString.Include
    private transient WMSError error;

    protected UnitException(String message) {
        super(message);
    }

    protected UnitException(String message, Object[] parameters) {
        super(message, parameters);
    }

    protected UnitException(WMSError error, String message, Object[] parameters) {
        super(error, message, parameters);
        this.error = error;
    }

    protected UnitException(WMSError error, String message) {
        super(error, message);
        this.error = error;
    }

    public UnitException(WMSError error, Object[] parameters) {
        super(error, parameters);
        this.error = error;
    }

    @Override
    public String getSubDomain() {
        return "Unit";
    }
}
