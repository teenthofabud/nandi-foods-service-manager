package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.error;

import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSError;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UOMException extends WMSBaseException {

    @ToString.Include
    private transient WMSError error;

    public UOMException(WMSError error, Object[] parameters) {
        super(error, parameters);
        this.error = error;
    }

    @Override
    public String getDomain() {
        return "Settings";
    }

    @Override
    public String getSubDomain() {
        return "UOM";
    }


}
