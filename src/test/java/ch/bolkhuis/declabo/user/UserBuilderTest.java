package ch.bolkhuis.declabo.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserBuilderTest {

    private static final transient String LEGAL_EMAIL = "hendrik@bolkhuis.ch";
    private static final transient long LEGAL_ID = 36L;
    private static final transient String LEGAL_NAME = "Hendrik";
    private static final transient String LEGAL_NICKNAME = "Henkie";
    private static final transient String LEGAL_SURNAME = "Pool, van der";

    @Test
    public void test_whenAllFieldsAreValid_thenUserIsBuild() {
        User.Builder builder = User.builder();
        User user = builder
                .setId(LEGAL_ID)
                .setEmail(LEGAL_EMAIL)
                .setName(LEGAL_NAME)
                .setSurname(LEGAL_SURNAME)
                .setNickname(LEGAL_NICKNAME)
                .build();
        Assertions.assertEquals(LEGAL_ID, user.getId());
        Assertions.assertEquals(LEGAL_EMAIL, user.getEmail());
        Assertions.assertEquals(LEGAL_NAME, user.getName());
        Assertions.assertEquals(LEGAL_SURNAME, user.getSurname());
        Assertions.assertEquals(LEGAL_NICKNAME, user.getNickname());
    }

    @Test
    public void test_whenBuilderIsReused_thenPreviousValuesAreForgotten() {
        User.Builder builder = User.builder();
        User user = builder
                .setId(LEGAL_ID)
                .setEmail(LEGAL_EMAIL)
                .setName(LEGAL_NAME)
                .setSurname(LEGAL_SURNAME)
                .setNickname(LEGAL_NICKNAME)
                .build();
        User anotherUser = builder
                .setId(LEGAL_ID)
                .setEmail("another.legal.email@gmail.com")
                .setName(LEGAL_NAME)
                .setSurname(LEGAL_SURNAME)
                .setNickname(LEGAL_NICKNAME)
                .build();
        Assertions.assertNotEquals(user.getEmail(), anotherUser.getEmail());
    }

    @Test
    public void test_whenSettingInvalidFields_thenBuildThrows() {
        User.Builder builder = User.builder();
        builder
                .setEmail("")
                .setName("")
                .setSurname("")
                .setNickname("");
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setId(LEGAL_ID);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setEmail(LEGAL_EMAIL);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setName(LEGAL_NAME);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setSurname(LEGAL_SURNAME);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setNickname(null);
        builder.build();
    }

    @Test
    public void test_whenBuilderUserConstructor_thenFieldsAreCopied() {
        User user = new User(0L, LEGAL_EMAIL, LEGAL_NAME, LEGAL_SURNAME, LEGAL_NICKNAME);
        User anotherUser = User.builder(user).build();
        Assertions.assertEquals(user.getId(), anotherUser.getId());
        Assertions.assertEquals(user.getEmail(), anotherUser.getEmail());
        Assertions.assertEquals(user.getName(), anotherUser.getName());
        Assertions.assertEquals(user.getSurname(), anotherUser.getSurname());
        Assertions.assertEquals(user.getNickname(), anotherUser.getNickname());
    }
}
