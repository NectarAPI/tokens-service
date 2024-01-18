package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidKeyRevisionNumber;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class KeyRevisionNumberTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatKeyRevisionNumberReturnsRequiredProperties() {
        try {
            int keyRevisionNo = 1;
            KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(keyRevisionNo);
            assertEquals("test that the generated keys revision number is correct", 1, keyRevisionNumber.getValue());
            assertEquals("test that the correct name is returned", "Key Revision Number", keyRevisionNumber.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidKeyRevisionValuesThrowAnException()
        throws InvalidKeyRevisionNumber {
        int keyRevisionNo = 0;
        expectedException.expect(InvalidKeyRevisionNumber.class);
        expectedException.expectMessage("Invalid Key Revision Number");
        new KeyRevisionNumber(keyRevisionNo);
    }

}