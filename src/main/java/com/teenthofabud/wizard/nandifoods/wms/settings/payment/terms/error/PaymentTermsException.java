package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.error;

import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSError;

public class PaymentTermsException extends WMSBaseException {

    public PaymentTermsException(WMSError error, Object[] parameters) {
        super(error, parameters);
    }

    @Override
    public String getDomain() {
        return "Settings";
    }

    @Override
    public String getSubDomain() {
        return "Payment Terms";
    }
}
