package com.teenthofabud.wizard.nandifoods.wms.audit;

public interface Auditable {

    Audit getAudit();

    void setAudit(Audit audit);
}