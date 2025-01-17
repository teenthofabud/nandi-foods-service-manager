package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity;


import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.AuditListener;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "PaymentTermsEntity")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Table(name = "payment_terms",
        indexes = {
                @Index(columnList = "code", name = "idx_payment_terms_code"),
                @Index(columnList = "name", name = "idx_payment_terms_name")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uix_payment_terms_code", columnNames = { "code" })
        }
)
public class PaymentTermsEntity implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Embedded
    private Audit audit;

    @Column(name = "code", nullable = false)
    @EqualsAndHashCode.Include
    private String code;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "days_until_due", nullable = false)
    @EqualsAndHashCode.Include
    private Integer daysUntilDue;


}
