package uk.me.ruck.vyne.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uk.me.ruck.vyne.dto.TransactionDto;
import uk.me.ruck.vyne.model.Transaction;
import uk.me.ruck.vyne.model.TransactionSpecification;
import uk.me.ruck.vyne.service.TransactionService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<TransactionDto> getTransactions(){
        log.info("Get all transactions");
        return transactionService.listTransactions();
    }

    @PostMapping("/transactions/search")
    public List<TransactionDto> search(@RequestBody Transaction search) {
        log.info("Transaction search: {}", search);
        return transactionService.searchTransactions(new TransactionSpecification(search));
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(@RequestBody Transaction transaction){
        log.info("Create transaction: {}",transaction);
        return transactionService.createTransaction(transaction);
    }

    @PatchMapping("/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransactionDto patchTransaction(@RequestBody Transaction transaction, @PathVariable Long transactionId){
        log.info("Patch transaction '{}': {}",transactionId, transaction);
        try {
            return transactionService.patchTransaction(transactionId, transaction);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Not Found", e);
        }
    }

    @DeleteMapping("/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTransaction(@PathVariable Long transactionId){
        log.info("Delete transaction '{}'",transactionId);
        try {
            transactionService.deleteTransaction(transactionId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Not Found", e);
        }
    }

}
