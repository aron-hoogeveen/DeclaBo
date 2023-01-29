package ch.bolkhuis.declabo.fund;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//CHECKSTYLE:ON

@SuppressWarnings("PMD")
@WebMvcTest(FundController.class)
@Import({
        FundModelAssembler.class
})
public class FundControllerTest {

    private static final String BASE_PATH = "http://localhost/api/funds";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FundRepository repository;

    private Fund[] funds;

    //CHECKSTYLE:OFF
    private final String f1_name = "Credit Fund 1";
    private final long f1_balance = 42L;
    private final long f1_id = 1L;

    private final String f2_name = "Debit Fund 1";
    private final long f2_balance = 36L;
    private final long f2_id = 2L;
    //CHECKSTYLE:ON

    /**
     * Create new funds before each execution.
     */
    @BeforeEach
    public void init() {
        funds = new Fund[] {
            new CreditFund(f1_name, f1_balance),
            new DebitFund(f2_name, f2_balance),
        };
        funds[0].setId(f1_id);
        funds[1].setId(f2_id);
    }

    @Test
    public void should_return_all_funds() throws Exception {
        init_repository_with_two_funds();

        mvc.perform(get(BASE_PATH).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.creditFunds[0].id").value(f1_id))
                .andExpect(jsonPath("$._embedded.creditFunds[0].name").value(f1_name))
                .andExpect(jsonPath("$._embedded.creditFunds[0].balance").value(f1_balance))
                .andExpect(jsonPath("$._embedded.creditFunds[0].type").value(funds[0].getType()))
                .andExpect(jsonPath("$._embedded.debitFunds[0].id").value(f2_id))
                .andExpect(jsonPath("$._embedded.debitFunds[0].name").value(f2_name))
                .andExpect(jsonPath("$._embedded.debitFunds[0].balance").value(f2_balance))
                .andExpect(jsonPath("$._embedded.debitFunds[0].type").value(funds[1].getType()));
    }

    @Test
    public void should_return_fund_when_get_by_id_for_credit() throws Exception {
        init_repository_with_two_funds();

        ResultActions result = mvc.perform(get(BASE_PATH + "/" + f1_id).accept(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isOk());
        verifySingleJson(result, funds[0]);
    }

    @Test
    public void should_return_fund_when_get_by_id_for_debit() throws Exception {
        init_repository_with_two_funds();

        ResultActions result = mvc.perform(get(BASE_PATH + "/" + f2_id).accept(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isOk());
        verifySingleJson(result, funds[1]);
    }

    @Test
    public void should_return_notFound_when_nonexisting_id() throws Exception {
        init_repository_with_two_funds();

        ResultActions result = mvc.perform(get(BASE_PATH + "/404").accept(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print());
        result.andExpect(status().isNotFound());
    }

    @Test
    public void should_create_debitfund_on_post() throws Exception {
        init_repository_with_two_funds();

        long newId = 666L;
        String newName = "New Debit Fund";
        long newBalance = 50L;
        DebitFund savedFund = new DebitFund(newName, newBalance);
        savedFund.setId(newId);

        String content = "{\"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(DebitFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(post(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isCreated())
                .andExpect(header().string(
                        HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + newId)
            );
        verifySingleJson(result, savedFund);
    }

    @Test
    public void should_reject_create_for_debitfund_with_initial_id() throws Exception {
        init_repository_with_two_funds();

        long newId = 666L;
        String newName = "New Debit Fund";
        long newBalance = 50L;
        DebitFund savedFund = new DebitFund(newName, newBalance);
        savedFund.setId(newId);

        String content = "{\"id\": " + newId + ", \"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(DebitFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(post(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result
                .andDo(print())
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void should_reject_create_for_creditfund_with_initial_id() throws Exception {
        init_repository_with_two_funds();

        long newId = 666L;
        String newName = "New Debit Fund";
        long newBalance = 50L;
        CreditFund savedFund = new CreditFund(newName, newBalance);
        savedFund.setId(newId);

        String content = "{\"id\": " + newId + ", \"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(CreditFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(post(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result
                .andDo(print())
                .andExpect(status().isBadRequest()); // FIXME I think it is better to return the same type of body as for Validation exceptions.
//                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void should_create_creditfund_on_post() throws Exception {
        init_repository_with_two_funds();

        long newId = 666L;
        String newName = "New Credit Fund";
        long newBalance = 50L;
        CreditFund savedFund = new CreditFund(newName, newBalance);
        savedFund.setId(newId);

        String content = "{\"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(CreditFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(post(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isCreated())
                .andExpect(header().string(
                        HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + newId)
            );
        verifySingleJson(result, savedFund);
    }

    @Test
    public void should_reject_fund_with_data_constraint_violations_debit() throws Exception {
        init_repository_with_two_funds();

        String name = "existing name";
        given(repository.existsByName(name)).willReturn(true);

        // assume that there is already a fund with name "existing name"
        String content = "{\"name\": \"" + name + "\", \"balance\": 0}";

        ResultActions result = mvc.perform(post(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("This name already exists"));
    }

    @Test
    public void should_reject_fund_with_data_constraint_violations_credit() throws Exception {
        init_repository_with_two_funds();

        String name = "existing name";
        given(repository.existsByName(name)).willReturn(true);

        // assume that there is already a fund with name "existing name"
        String content = "{\"name\": \"" + name + "\", \"balance\": 0}";

        ResultActions result = mvc.perform(post(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("This name already exists"));
    }

    @Test
    public void should_reject_fund_with_missing_fields_for_debitfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{}";
        ResultActions result = mvc.perform(post(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void should_reject_fund_with_missing_fields_for_creditfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{}";
        ResultActions result = mvc.perform(post(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void should_reject_blank_fields_for_debitfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{\"name\": \"\", \"balance\": " + 0 + ", \"type\": \"\"}";

        ResultActions result = mvc.perform(post(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void should_reject_blank_fields_for_creditfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{\"name\": \"\", \"balance\": " + 0 + ", \"type\": \"\"}";

        ResultActions result = mvc.perform(post(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void should_return_status_created_if_put_for_new_debitfund() throws Exception {
        long newId = 666L;
        String newName = "New Debit Fund";
        long newBalance = 50L;
        DebitFund savedFund = new DebitFund(newName, newBalance);
        savedFund.setId(newId);

        // note that the new Fund has no `id` field.
        String content = "{\"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(DebitFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(put(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + newId));
        verifySingleJson(result, savedFund);
    }

    @Test
    public void should_return_status_created_if_put_for_new_creditfund() throws Exception {
        long newId = 666L;
        String newName = "New Credit Fund";
        long newBalance = 50L;
        CreditFund savedFund = new CreditFund(newName, newBalance);
        savedFund.setId(newId);

        // note that the new Fund has no `id` field.
        String content = "{\"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(CreditFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(put(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + newId));
        verifySingleJson(result, savedFund);
    }

    @Test
    public void should_reject_blank_fields_for_put_debitfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{\"name\": \"\", \"balance\": " + 0 + "}";

        ResultActions result = mvc.perform(put(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void should_reject_blank_fields_for_put_creditfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{\"name\": \"\", \"balance\": " + 0 + "}";

        ResultActions result = mvc.perform(put(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void should_return_status_ok_if_put_for_new_debitfund() throws Exception {
        long newId = 666L;
        String newName = "New Debit Fund";
        long newBalance = 50L;
        DebitFund savedFund = new DebitFund(newName, newBalance);
        savedFund.setId(newId);

        // note that the new Fund has already an `id` field
        String content = "{\"id\": " + newId + ", \"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(DebitFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(put(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + newId));
        verifySingleJson(result, savedFund);
    }

    @Test
    public void should_return_status_ok_if_put_for_new_creditfund() throws Exception {
        long newId = 666L;
        String newName = "New Credit Fund";
        long newBalance = 50L;
        CreditFund savedFund = new CreditFund(newName, newBalance);
        savedFund.setId(newId);

        // note that the new Fund has already an `id` field
        String content = "{\"id\": " + newId + ", \"name\": \"" + newName + "\", \"balance\": " + newBalance + "}";

        given(repository.saveAndFlush(any(CreditFund.class))).willReturn(savedFund);

        ResultActions result = mvc.perform(put(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(content)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + newId));
        verifySingleJson(result, savedFund);
    }

    @Test
    public void should_reject_put_on_missing_fields_for_debitfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{}";
        ResultActions result = mvc.perform(put(BASE_PATH + "/debit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void should_reject_put_on_missing_fields_for_creditfund() throws Exception {
        init_repository_with_two_funds();

        String halJsonValue = "{}";
        ResultActions result = mvc.perform(put(BASE_PATH + "/credit")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    // FIXME following tests should be worked out and implemented in the controller class.
//    @Test
//    public void should_return_bad_request_for_put_debit_with_constraint_violation() throws Exception {
//        init_repository_with_two_funds();
//
//        // FIXME see next comment about implementation
//        /*
//         * If we are updating an existing fund and we are not changing the name, we should not
//         * return a BAD_REQUEST with a constraint violation for field NAME. However, if we are
//         * updating an existing fund and we want to change the name to an existing different name
//         * we SHOULD receive a BAD_REQUEST with a constraint violation for field NAME.
//         */
//
//        // assume that the name already exists
//        String name = "existing name";
//        given(repository.existsByName(name)).willReturn(true);
//
//        String content = "{\"name\": \"" + name + "\"}";
//
//        ResultActions result = mvc.perform(put(BASE_PATH + "debit")
//                .accept(MediaTypes.HAL_JSON_VALUE)
//                .content(content)
//                .contentType(MediaTypes.HAL_JSON_VALUE));
//
//        result.andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.name").value(FundController.errorMessages.get("constraintName")));
//    }
//
//    @Test
//    public void should_return_bad_request_for_put_credit_with_constraint_violation() throws Exception {
//        init_repository_with_two_funds();
//
//        // assume that the name already exists
//        String name = "existing name";
//        given(repository.existsByName(name)).willReturn(true);
//
//        String content = "{\"name\": \"" + name + "\"}";
//
//        ResultActions result = mvc.perform(put(BASE_PATH + "credit")
//                .accept(MediaTypes.HAL_JSON_VALUE)
//                .content(content)
//                .contentType(MediaTypes.HAL_JSON_VALUE));
//
//        result.andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.name").value(FundController.errorMessages.get("constraintName")));
//    }

    private void init_repository_with_two_funds() {
        given(repository.findAll()).willReturn(Arrays.asList(funds));
        given(repository.findById(f1_id)).willReturn(Optional.of(funds[0]));
        given(repository.findById(f2_id)).willReturn(Optional.of(funds[1]));
    }

    private void verifySingleJson(ResultActions action, Fund fund) throws Exception {
        action
                .andDo(print())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(fund.getId()))
                .andExpect(jsonPath("$.name").value(fund.getName()))
                .andExpect(jsonPath("$.balance").value(fund.getBalance()))
                .andExpect(jsonPath("$.type").value(fund.getType()))
                .andExpect(jsonPath("$._links.self.href").value(BASE_PATH + "/" + fund.getId()))
                .andExpect(jsonPath("$._links.funds.href").value(BASE_PATH));
    }

}
