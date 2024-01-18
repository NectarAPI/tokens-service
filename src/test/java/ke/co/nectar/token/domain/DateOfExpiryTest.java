package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidDateOtExpiryException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DateOfExpiryTest {

    private DateOfExpiry dateOfExpiry ;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatTheCorrectDOEParametersAreReturned() {
        try {
            final String TEST_DOE = "1201";
            dateOfExpiry = new DateOfExpiry(TEST_DOE);
            assertEquals("test that the correct date of expiry is returned", TEST_DOE, dateOfExpiry.getValue());
            assertEquals("test that the correct date of expiry name is returned", "Date Of Expiry", dateOfExpiry.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidParametersThrowAnException()
        throws InvalidDateOtExpiryException {
        final String INVALID_TEST_DOE = "9915";
        expectedException.expect(InvalidDateOtExpiryException.class);
        expectedException.expectMessage("Invalid Date of Expiry Exception");
        dateOfExpiry = new DateOfExpiry(INVALID_TEST_DOE);
    }

}