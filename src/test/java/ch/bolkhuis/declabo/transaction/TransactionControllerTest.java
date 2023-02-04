package ch.bolkhuis.declabo.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TransactionController.class)
@Import(TransactionModelAssembler.class)
public class TransactionControllerTest {

    private static final String BASE_PATH = "http://localhost/api/transactions";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TransactionRepository repository;

    private Transaction[] transactions;

    //CHECKSTYLE:OFF

    //CHECKSTYLE:ON

    /*
     * {
     *    "id": 0,
     *    "debtor.id": 2,
     *    "debtor.name": "Aron",
     *    "creditor.id" 3,
     *    "creditor.name": "Huisch",
     *    "amount": 56.99,
     *    "date": "2023-02-04",
     *    "description": "Makrorun algemene voorraad",
     *    "submission": null,
     *    "event": null,
     *    "settled": false
     * }
     */

    private void verifySingleJson(ResultActions action, Transaction transaction) throws Exception {
        action.andDo(print())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(transaction.getId()))
                .andExpect(jsonPath("$.debtor."))
    }
}
