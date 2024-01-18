package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidIssuerIdentificationNumberException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class IssuerIdentificationNumberTest {

    private IssuerIdentificationNumber issuerIdentificationNumber ;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testThatCorrectIssuerIdentificationNumberIsValid() {
        try {
            String iinNumber = "123456";
            issuerIdentificationNumber = new IssuerIdentificationNumber(iinNumber);
            assertEquals("test that the correct issuer identification number is returned", "123456", issuerIdentificationNumber.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatExceptionIsThrownIfIdentificationNumberIsInvalid()
        throws InvalidIssuerIdentificationNumberException {
        String iinNumber = "1234567";
        thrown.expect(InvalidIssuerIdentificationNumberException.class);
        thrown.expectMessage("Invalid Issuer Identification Number");
        issuerIdentificationNumber = new IssuerIdentificationNumber(iinNumber);
    }

}