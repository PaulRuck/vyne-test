package uk.me.ruck.vyne.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private TransactionStatus status;
    private BigDecimal amount;
    private CurrencyCode currency;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
}
