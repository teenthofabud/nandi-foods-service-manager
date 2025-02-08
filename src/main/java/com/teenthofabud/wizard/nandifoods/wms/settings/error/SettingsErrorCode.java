package com.teenthofabud.wizard.nandifoods.wms.settings.error;


import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSError;

public enum SettingsErrorCode implements WMSError {

    SETTINGS_ATTRIBUTE_INVALID("VC-PR-002", 400), // syntactic
    SETTINGS_NOT_FOUND("VC-PR-006", 404),
    SETTINGS_ATTRIBUTE_UNEXPECTED("VC-PR-007", 422), // semantic
    SETTINGS_EXISTS("VC-PR-005", 409),
    SETTINGS_INACTIVE("VC-PR-008", 400),
    SETTINGS_ACTION_FAILURE("VC-PR-001", 500);

    private String errorCode;
    private int httpStatusCode;

    private SettingsErrorCode(String errorCode, int httpStatusCode) {
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String toString() {
        return "SettingsErrorCode{" +
                this.name() + " -> " +
                "errorCode='" + errorCode + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                '}';
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public String getDomain() {
        return "Settings";
    }

}
