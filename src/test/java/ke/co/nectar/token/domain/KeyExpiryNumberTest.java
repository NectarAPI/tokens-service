package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidKeyExpiryNumberException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class KeyExpiryNumberTest {

    private KeyExpiryNumber keyExpiryNumber;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatCorrectKeyExpiryNumberValuesAreReturned() {
        try {
            long testKeyExpiryNumber = 100;
            keyExpiryNumber = new KeyExpiryNumber(testKeyExpiryNumber);
            assertEquals("test that the correct keys expiry number is returned", 100, keyExpiryNumber.getValue());
            assertEquals("test that the correct name is returned", "Key Expiry Number", keyExpiryNumber.getName());

            BitString testKeyBitString = new BitString(34l);
            keyExpiryNumber = new KeyExpiryNumber(testKeyBitString);
            assertEquals("test that the correct keys expiry number is returned", 34, keyExpiryNumber.getValue());
            assertEquals("test that the correct name is returned", "Key Expiry Number", keyExpiryNumber.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidKeyExpiryParametersAreReturned()
        throws InvalidKeyExpiryNumberException {

        long testKeyExpiryNumber = 256;
        expectedException.expect(InvalidKeyExpiryNumberException.class);
        expectedException.expectMessage("Invalid Key Expiry Number");
        keyExpiryNumber = new KeyExpiryNumber(testKeyExpiryNumber);
        assertEquals("test that the correct keys expiry number is returned", 100, keyExpiryNumber.getValue());
        assertEquals("test that the correct name is returned", "Key Expiry Number", keyExpiryNumber.getName());

        BitString testKeyBitString = new BitString(304l);
        expectedException.expect(InvalidKeyExpiryNumberException.class);
        expectedException.expectMessage("Invalid Key Expiry Number");
        keyExpiryNumber = new KeyExpiryNumber(testKeyBitString);
        assertEquals("test that the correct keys expiry number is returned", 34, keyExpiryNumber.getValue());
        assertEquals("test that the correct name is returned", "Key Expiry Number", keyExpiryNumber.getName());
    }

}