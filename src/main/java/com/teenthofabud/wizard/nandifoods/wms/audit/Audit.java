package com.teenthofabud.wizard.nandifoods.wms.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Access(AccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Audit {

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "approval_time")
    private LocalDateTime approvalTime;

    @Column(name = "modification_time")
    private LocalDateTime modificationTime;

    @Column(name = "modified_by")
    private String modifiedBy;

}
