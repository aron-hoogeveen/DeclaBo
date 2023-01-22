package ch.bolkhuis.declabo.user;

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
@WebMvcTest(UserController.class)
@Import({
        UserModelAssembler.class
})
public class UserControllerTest {

    private static final String BASE_PATH = "http://localhost/api/users";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserRepository repository;

    private User[] users;

    //CHECKSTYLE:OFF
    private final String u1_email = "john@bolkhuis.ch";
    private final String u1_name = "John";
    private final String u1_surname = "Smith";
    private final int u1_room = 1;
    private final long u1_id = 1L;

    private final String u2_email = "jane@bolkhuis.ch";
    private final String u2_name = "Jane";
    private final String u2_surname = "Smith";
    private final int u2_room = 2;
    private final long u2_id = 2L;
    //CHECKSTYLE:ON

    /**
     * Create new users before each execution.
     */
    @BeforeEach
    public void init() {
        users = new User[] {
            new User(u1_email, u1_name, u1_surname, u1_room),
            new User(u2_email, u2_name, u2_surname, u2_room)
        };
        users[0].setId(u1_id);
        users[1].setId(u2_id);
    }

    @Test
    public void should_return_all_users_when_get_users() throws Exception {
        init_repository_with_two_users();

        mvc.perform(get(BASE_PATH).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.users[0].id").value(u1_id))
                .andExpect(jsonPath("$._embedded.users[0].email").value(u1_email))
                .andExpect(jsonPath("$._embedded.users[0].name").value(u1_name))
                .andExpect(jsonPath("$._embedded.users[0].surname").value(u1_surname))
                .andExpect(jsonPath("$._embedded.users[0].room").value(u1_room))
                .andExpect(jsonPath("$._embedded.users[1].id").value(u2_id))
                .andExpect(jsonPath("$._embedded.users[1].email").value(u2_email))
                .andExpect(jsonPath("$._embedded.users[1].name").value(u2_name))
                .andExpect(jsonPath("$._embedded.users[1].surname").value(u2_surname))
                .andExpect(jsonPath("$._embedded.users[1].room").value(u2_room));
    }

    /**
     * GET {base}/users/{id}.
     */
    @Test
    public void should_return_user_when_get_by_id() throws Exception {
        init_repository_with_two_users();

        ResultActions result = mvc.perform(get(BASE_PATH + "/" + u1_id).accept(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isOk());
        verifySingleJson(result, users[0]);
    }

    @Test
    public void should_return_not_found_when_getting_non_existing_user() throws Exception {
        ResultActions result = mvc.perform(get(BASE_PATH + "/" + 404).accept(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print());
        result.andExpect(status().isNotFound());
    }

    @Test
    public void should_create_user_on_post() throws Exception {
        init_repository_with_two_users();

        String newEmail = "new@bolkhuis.ch";
        String newName = "New";
        String newSurname = "Surname";
        int newRoom = 5;
        User newUser = new User(newEmail, newName, newSurname, newRoom);
        User savedUser = new User(newEmail, newName, newSurname, newRoom);
        savedUser.setId(3L);

        given(repository.saveAndFlush(any(User.class))).willReturn(savedUser);

        ResultActions result = mvc.perform(post(BASE_PATH)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(mapper.writeValueAsString(newUser))
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isCreated())
                .andExpect(header().string(
                        HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + savedUser.getId())
            );
        verifySingleJson(result, savedUser);
    }

    @Test
    public void should_reject_user_with_missing_fields() throws Exception {
        init_repository_with_two_users();

        // leave out email
        String halJsonValue = "{}";
        ResultActions result = mvc.perform(post(BASE_PATH)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists());
        // TODO also check for error on missing room field. This is related to the FIXME in class User.
    }

    @Test
    public void should_reject_user_with_blank_fields_that_should_be_notblank() throws Exception {
        init_repository_with_two_users();

        String halJsonValue = "{\"name\": \"\", \"surname\": \"\", \"email\": \"\", \"room\": 1}";
        ResultActions result = mvc.perform(post(BASE_PATH)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists());
        // TODO change room number to `null` after converting to Boxed Integer.
    }

    @Test
    public void should_return_status_created_if_put_for_new_user() throws Exception {
        String newEmail = "new@bolkhuis.ch";
        String newName = "New";
        String newSurname = "Surname";
        int newRoom = 5;
        User newUser = new User(newEmail, newName, newSurname, newRoom);
        // note that the newUser has no ID field
        User savedUser = new User(newEmail, newName, newSurname, newRoom);
        savedUser.setId(3L);

        given(repository.saveAndFlush(any(User.class))).willReturn(savedUser);

        ResultActions result = mvc.perform(put(BASE_PATH)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(mapper.writeValueAsString(newUser))
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + savedUser.getId()));
        verifySingleJson(result, savedUser);
    }

    @Test
    public void should_return_status_ok_if_put_for_existing_user() throws Exception {
        String newEmail = "new@bolkhuis.ch";
        String newName = "New";
        String newSurname = "Surname";
        int newRoom = 5;
        User newUser = new User(newEmail, newName, newSurname, newRoom);
        newUser.setId(3L);
        // note that the newUser already has the ID field set
        User savedUser = new User(newEmail, newName, newSurname, newRoom);
        savedUser.setId(3L);

        given(repository.saveAndFlush(any(User.class))).willReturn(savedUser);

        ResultActions result = mvc.perform(put(BASE_PATH)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(mapper.writeValueAsString(newUser))
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_LOCATION, BASE_PATH + "/" + savedUser.getId()));
        verifySingleJson(result, savedUser);

    }

    @Test
    public void should_reject_put_for_invalid_body() throws Exception {
        init_repository_with_two_users();

        // leave out email
        String halJsonValue = "{}";
        ResultActions result = mvc.perform(put(BASE_PATH)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists());
        // TODO also check for error on missing room field. This is related to the FIXME in class User.
    }

    @Test
    public void should_reject_put_for_blank_fields() throws Exception {
        init_repository_with_two_users();

        // leave out email
        String halJsonValue = "{\"id\": null, \"name\": \"\", \"surname\": \"\", \"email\": \"\", \"room\": 1}";
        ResultActions result = mvc.perform(put(BASE_PATH)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(halJsonValue)
                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists());
        // TODO also check for error on missing room field. This is related to the FIXME in class User.
    }

    private void init_repository_with_two_users() {
        given(repository.findAll()).willReturn(Arrays.asList(users));
        given(repository.findById(u1_id)).willReturn(Optional.of(users[0]));
        given(repository.findById(u2_id)).willReturn(Optional.of(users[1]));
    }

    private void verifySingleJson(ResultActions action, User user) throws Exception {
        action
                .andDo(print())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.surname").value(user.getSurname()))
                .andExpect(jsonPath("$.room").value(user.getRoom()))
                .andExpect(jsonPath("$._links.self.href").value(BASE_PATH + "/" + user.getId()))
                .andExpect(jsonPath("$._links.users.href").value(BASE_PATH));
    }

}
