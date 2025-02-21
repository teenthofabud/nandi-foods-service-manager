package com.teenthofabud.wizard.nandifoods.wms.error.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class WMSBaseException extends Exception {
    private transient WMSError error;
    private transient Object[] parameters;
    private transient String domain;
    private transient String subDomain;

    protected WMSBaseException(WMSError error, Object[] parameters) {
        super(error.getErrorCode());
        this.error = error;
        this.parameters = parameters;
    }

    public abstract String getDomain();

    public abstract String getSubDomain();

}
