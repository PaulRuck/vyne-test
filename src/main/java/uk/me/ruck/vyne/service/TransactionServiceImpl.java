package uk.me.ruck.vyne.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uk.me.ruck.vyne.dto.TransactionDto;
import uk.me.ruck.vyne.model.Transaction;
import uk.me.ruck.vyne.repository.TransactionRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionDto> listTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDto> searchTransactions(Specification<Transaction> spec) {
        return transactionRepository.findAll(spec);
    }

    @Transactional
    @Override
    public TransactionDto createTransaction(Transaction transaction) {
        TransactionDto transactionDto = TransactionDto.builder()
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .description(transaction.getDescription())
                .build();

        log.info("Saving transaction: {}",transactionDto);

        return transactionRepository.save(transactionDto);
    }

    @Transactional
    @Override
    public TransactionDto patchTransaction(Long transactionId, Transaction transaction){
        TransactionDto transactionDto = transactionRepository.getById(transactionId);

        if(transaction.getAmount() != null) {
            transactionDto.setAmount(transaction.getAmount());
        }
        if(transaction.getCurrency() != null) {
            transactionDto.setCurrency(transaction.getCurrency());
        }
        if(transaction.getDescription() != null) {
            transactionDto.setDescription(transaction.getDescription());
        }
        if(transaction.getStatus() != null) {
            transactionDto.setStatus(transaction.getStatus());
        }
        return transactionRepository.save(transactionDto);
    }

    @Transactional
    @Override
    public void deleteTransaction(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }
}
