package com.teenthofabud.wizard.nandifoods.wms.error.core;

public abstract class WMSBaseException extends Exception {
    private transient String message;
    private transient WMSError error;
    private transient Object[] parameters;
    private transient SubDomain subDomain;

    protected WMSBaseException(String message) {
        super(message);
        this.message = message;
        this.error = null;
    }

    protected WMSBaseException(String message, Object[] parameters) {
        super(message);
        this.message = message;
        this.error = null;
        this.parameters = parameters;
    }

    protected WMSBaseException(WMSError error, String message, Object[] parameters) {
        super(message);
        this.error = error;
        this.message = message;
        this.parameters = parameters;
    }

    protected WMSBaseException(WMSError error, String message) {
        super(message);
        this.error = error;
        this.message = message;
    }

    protected WMSBaseException(WMSError error, Object[] parameters) {
        super(error.getErrorCode());
        this.error = error;
        this.message = "";
        this.parameters = parameters;
    }


    public abstract String getSubDomain();

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(WMSError error) {
        this.error = error;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setSubDomain(SubDomain subDomain) {
        this.subDomain = subDomain;
    }

    public String getMessage() {
        return this.message;
    }

    public WMSError getError() {
        return this.error;
    }

    public Object[] getParameters() {
        return this.parameters;
    }
}
