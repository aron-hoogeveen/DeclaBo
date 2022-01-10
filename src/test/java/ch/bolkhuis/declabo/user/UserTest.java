package ch.bolkhuis.declabo.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link User}.
 */
public class UserTest {

    private static final transient String LEGAL_EMAIL = "hendrik@bolkhuis.ch";
    private static final transient String LEGAL_NAME = "Hendrik";
    private static final transient String LEGAL_NICKNAME = "Gekke Hendrik";
    private static final transient String LEGAL_SURNAME = "Putten, van der";

    @Test
    public void testConstructor_whenNullForNonNull_thenThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new User(0, null, LEGAL_NAME, LEGAL_SURNAME, null)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new User(0, LEGAL_EMAIL, null, LEGAL_SURNAME, null)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new User(0, LEGAL_EMAIL, LEGAL_NAME, null, null)
        );
        new User(0, LEGAL_EMAIL, LEGAL_NAME, LEGAL_SURNAME, null);
        new User(0, LEGAL_EMAIL, LEGAL_NAME, LEGAL_SURNAME, LEGAL_NICKNAME);
    }

    @Test
    public void test_whenNameIsEmpty_thenItIsInvalid() {
        String blankString = "   ";
        Assertions.assertFalse(User.isValidName(blankString));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new User(0, LEGAL_EMAIL, blankString, LEGAL_SURNAME, null)
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new User(0, LEGAL_EMAIL, LEGAL_NAME, blankString, null)
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new User(0, LEGAL_EMAIL, LEGAL_NAME, LEGAL_SURNAME, blankString)
        );
    }

    /**
     * The choice for very lenient email correction checking is made, because checking for email
     * correctness is a very hard task. The services that will make use of this User class must
     * make sure that they validate the email addresses themselves.
     */
    @Test
    public void test_whenInvalidEmailAddress_thenConstructorThrows() {
        String invalidEmail = "  ";
        Assertions.assertFalse(User.isValidEmail(invalidEmail));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new User(0, invalidEmail, LEGAL_NAME, LEGAL_SURNAME, LEGAL_NICKNAME)
        );
    }

    @Test
    public void test_whenConstructed_thenGettersReturnCorrectValues() {
        int id = 0;
        User user = new User(id, LEGAL_EMAIL, LEGAL_NAME, LEGAL_SURNAME, LEGAL_NICKNAME);
        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(LEGAL_EMAIL, user.getEmail());
        Assertions.assertEquals(LEGAL_NAME, user.getName());
        Assertions.assertEquals(LEGAL_SURNAME, user.getSurname());
        Assertions.assertEquals(LEGAL_NICKNAME, user.getNickname());
    }
}
