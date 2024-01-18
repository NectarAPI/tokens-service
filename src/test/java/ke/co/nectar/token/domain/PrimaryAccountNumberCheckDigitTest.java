package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidPanCheckDigitException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PrimaryAccountNumberCheckDigitTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatThePanCheckDigitTestPropertiesReturnedAreCorrect() {
        try {
            int testPanCheckDigit = 1;
            PrimaryAccountNumberCheckDigit primaryAccountNumberCheckDigit = new PrimaryAccountNumberCheckDigit(testPanCheckDigit);
            assertEquals("test that the pan check digit returned is correct", testPanCheckDigit, primaryAccountNumberCheckDigit.getValue());
        } catch (InvalidPanCheckDigitException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatIncorrectPanCheckDigitValuesThrowException()
        throws InvalidPanCheckDigitException {
        int testPanCheckDigit = 12;
        expectedException.expectMessage("Invalid Pan Check Digit");
        expectedException.expect(InvalidPanCheckDigitException.class);
        PrimaryAccountNumberCheckDigit primaryAccountNumberCheckDigit = new PrimaryAccountNumberCheckDigit(testPanCheckDigit);
    }
}