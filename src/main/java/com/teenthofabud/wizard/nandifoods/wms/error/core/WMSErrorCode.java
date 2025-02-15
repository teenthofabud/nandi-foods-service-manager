package com.teenthofabud.wizard.nandifoods.wms.error.core;


import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSError;

public enum WMSErrorCode implements WMSError {

    WMS_ATTRIBUTE_INVALID("VC-PR-002", 400), // syntactic
    WMS_NOT_FOUND("VC-PR-006", 404),
    WMS_ATTRIBUTE_UNEXPECTED("VC-PR-007", 422), // semantic
    WMS_EXISTS("VC-PR-005", 409),
    WMS_INACTIVE("VC-PR-008", 400),
    WMS_ACTION_FAILURE("VC-PR-001", 500);

    private String errorCode;
    private int httpStatusCode;


     WMSErrorCode(String errorCode, int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
    }


    @Override
    public String toString() {
        return STR."WMSErrorCode{\{this.name()} -> errorCode='\{errorCode}\{'\''}, httpStatusCode=\{httpStatusCode}\{'}'}";
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }



}
