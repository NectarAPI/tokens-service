package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidKeyTypeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class KeyTypeTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatKeyTypeReturnsRequiredProperties() {
        try {
            int keyTypeNo = 1;
            KeyType keyType = new KeyType(keyTypeNo);
            assertEquals("test that the generated keys type is correct", 1, keyType.getValue());
            assertEquals("test that the correct name is returned", "Key Type", keyType.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidKeyTypeValuesThrowAnException()
            throws InvalidKeyTypeException {
        int keyRevisionNo = 4;
        expectedException.expect(InvalidKeyTypeException.class);
        expectedException.expectMessage("Invalid Key Type");
        KeyType keyType = new KeyType(keyRevisionNo);
    }

}