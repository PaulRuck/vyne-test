package uk.me.ruck.vyne.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uk.me.ruck.vyne.model.CurrencyCode;
import uk.me.ruck.vyne.model.TransactionStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_TRANSACTION",
        indexes = {
            @Index(name = "IDX_TRANSACTION_STATUS", columnList = "STATUS"),
            @Index(name = "IDX_TRANSACTION_CURRENCY", columnList = "CURRENCY"),
            @Index(name = "IDX_TRANSACTION_DESCRIPTION", columnList = "DESCRIPTION")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS", nullable = false)
    private TransactionStatus status;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false)
    private CurrencyCode currency;

    @Column(name = "DESCRIPTION")
    private String description;

    @CreationTimestamp
    @Column(name = "CREATED")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "UPDATED")
    private LocalDateTime updated;
}
