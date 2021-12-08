package uk.me.ruck.vyne.controller.v1;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.me.ruck.vyne.dto.TransactionDto;
import uk.me.ruck.vyne.model.CurrencyCode;
import uk.me.ruck.vyne.model.Transaction;
import uk.me.ruck.vyne.model.TransactionStatus;
import uk.me.ruck.vyne.repository.TransactionRepository;
import uk.me.ruck.vyne.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        createTransactionData();
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    void testAuthFailed() throws Exception {
        mockMvc.perform(
                get("/api/v1/transactions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuth() throws Exception {
        mockMvc.perform(
                get("/api/v1/transactions").with(httpBasic("username","password")))
                .andExpect(status().isOk());
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(
                get("/health"))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchWithAllFilters() throws Exception {
        Transaction searchCriteria = Transaction.builder()
                .currency(CurrencyCode.GBP)
                .description("test")
                .status(TransactionStatus.INITIALISED)
                .build();

        assertEquals(2, getApiSearchResponse(searchCriteria).size());
    }

    @Test
    void testSearchWithCurrencyFilter() throws Exception {
        Transaction searchCriteria = Transaction.builder()
                .currency(CurrencyCode.GBP)
                .build();

        assertEquals(3, getApiSearchResponse(searchCriteria).size());
    }

    @Test
    void testSearchWithStatusFilter() throws Exception {
        Transaction searchCriteria = Transaction.builder()
                .status(TransactionStatus.COMPLETED)
                .build();

        assertEquals(1, getApiSearchResponse(searchCriteria).size());
    }

    @Test
    void testSearchWithDescriptionLikeFilter() throws Exception {
        Transaction searchCriteria = Transaction.builder()
                .description("First")
                .build();

        assertEquals(1, getApiSearchResponse(searchCriteria).size());
    }

    private List<TransactionDto> getApiSearchResponse(Transaction searchCriteria) throws Exception {
        String apiResponse = mockMvc.perform(post("/api/v1/transactions/search")
                .with(httpBasic("username", "password"))
                .content(objectMapper.writeValueAsString(searchCriteria))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(apiResponse, new TypeReference<List<TransactionDto>>(){});
    }

    private void createTransactionData(){
        transactionService.createTransaction(Transaction.builder()
                .amount(BigDecimal.valueOf(123.45))
                .currency(CurrencyCode.GBP)
                .status(TransactionStatus.INITIALISED)
                .description("First test")
                .build());

        transactionService.createTransaction(Transaction.builder()
                .amount(BigDecimal.valueOf(432.23))
                .currency(CurrencyCode.GBP)
                .status(TransactionStatus.INITIALISED)
                .description("Second test")
                .build());

        transactionService.createTransaction(Transaction.builder()
                .amount(BigDecimal.valueOf(2.35))
                .currency(CurrencyCode.GBP)
                .status(TransactionStatus.COMPLETED)
                .description("Third")
                .build());
    }
}