package uk.me.ruck.vyne.service;

import org.springframework.data.jpa.domain.Specification;
import uk.me.ruck.vyne.dto.TransactionDto;
import uk.me.ruck.vyne.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> listTransactions();
    List<TransactionDto> searchTransactions(Specification<Transaction> spec);
    TransactionDto createTransaction(Transaction transaction);
    TransactionDto patchTransaction(Long transactionId, Transaction transaction);
    void deleteTransaction(Long transactionId);
}
