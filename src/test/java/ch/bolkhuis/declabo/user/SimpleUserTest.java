package ch.bolkhuis.declabo.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Test class for {@link SimpleUser}.
 */
public class SimpleUserTest {

    private static final transient Long ID = 0L;
    private static final transient String EMAIL = "hendrik@bolkhuis.ch";
    private static final transient String NAME = "Hendrik";
    private static final transient String NICKNAME = "Gekke Hendrik";
    private static final transient String SURNAME = "Putten, van der";

    @Test
    public void testConstructor_whenNullForNonNull_thenThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleUser(ID, null, NAME, SURNAME, null)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleUser(ID, EMAIL, null, SURNAME, null)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleUser(ID, EMAIL, NAME, null, null)
        );
        new SimpleUser(ID, EMAIL, NAME, SURNAME, null);
        new SimpleUser(ID, EMAIL, NAME, SURNAME, NICKNAME);
    }

    @Test
    public void test_whenNameIsEmpty_thenItIsInvalid() {
        String blankString = "   ";
        Assertions.assertFalse(SimpleUser.isValidName(blankString));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SimpleUser(ID, EMAIL, blankString, SURNAME, null)
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SimpleUser(ID, EMAIL, NAME, blankString, null)
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SimpleUser(ID, EMAIL, NAME, SURNAME, blankString)
        );
    }

    /**
     * The choice for very lenient email correction checking is made, because checking for email
     * correctness is a very hard task. The services that will make use of this SimpleUser class
     * must make sure that they validate the email addresses themselves.
     */
    @Test
    public void test_whenInvalidEmailAddress_thenConstructorThrows() {
        String invalidEmail = "  ";
        Assertions.assertFalse(SimpleUser.isValidEmail(invalidEmail));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SimpleUser(ID, invalidEmail, NAME, SURNAME, NICKNAME)
        );
    }

    @Test
    public void test_whenConstructed_thenGettersReturnCorrectValues() {
        SimpleUser simpleUser = new SimpleUser(ID, EMAIL, NAME, SURNAME, NICKNAME);
        Assertions.assertEquals(ID, simpleUser.getId());
        Assertions.assertEquals(EMAIL, simpleUser.getEmail());
        Assertions.assertEquals(NAME, simpleUser.getName());
        Assertions.assertEquals(SURNAME, simpleUser.getSurname());
        Optional<String> nicknameOptional = simpleUser.getNickname();
        Assertions.assertTrue(nicknameOptional.isPresent());
        Assertions.assertEquals(NICKNAME, nicknameOptional.get());
    }
}
