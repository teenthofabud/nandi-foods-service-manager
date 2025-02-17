package com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.error;

import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSError;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HUException extends WMSBaseException{

    @ToString.Include
    private transient WMSError error;

    public HUException(WMSError error, Object[] parameters) {
        super(error, parameters);
        this.error = error;
    }

    @Override
    public String getDomain() {
        return "Settings";
    }

    @Override
    public String getSubDomain() {
        return "Unit -> HU";
    }


}
