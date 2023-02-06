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
     * Requirements.
     *
     * How does a serialized Transaction look like?
     * {
     *    "id": 0,
     *    "debtor.id": 2,
     *    "debtor.name": "Voorraad",
     *    "creditor.id" 3,
     *    "creditor.name": "Aron",
     *    "amount": 56.99,
     *    "date": "2023-02-04",
     *    "description": "inkoop voorraad",
     *    "submission.id": 4,
     *    "submission.name": "Makrorun algemene voorraad",
     *    "event.id": null,
     *    "event.name": null,
     *    "settled": false
     * }
     * What does it look like when there is no submission? Will the field SUBMISSION.ID be null?
     *
     * What are some of the requirements?
     *   - If the transaction belongs to a submission, at least one of DEBTOR of CREDITOR must be
     *     equal to the paying party of that submission.
     *   - If the transaction belongs to an event, it cannot be created if the state of that event
     *     is _not_ OPEN. TODO create states for the events
     *   - If the transaction belongs to a submission, it cannot be created if the state of that
     *     submission is _not_ IN_PROGRESS.
     *   - Settled transactions cannot be changed whatsoever.
     */

    private void verifySingleJson(ResultActions action, Transaction transaction) throws Exception {
        action.andDo(print())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(transaction.getId()));
//                .andExpect(jsonPath("$.debtor."))
    }
}
