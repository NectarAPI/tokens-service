package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidBitStringException;
import ke.co.nectar.token.exceptions.InvalidManufacturerCodeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ManufacturerCodeTest {

    public ManufacturerCode manufacturerCode;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatManufacturerCodePropertiesAreCorrectlyReturned() {
        try {
            String testManufacturerCode = "12";
            manufacturerCode = new ManufacturerCode(testManufacturerCode);
            assertEquals("test that manufacturer code is correctly set", testManufacturerCode, manufacturerCode.getValue());

            testManufacturerCode = "1234";
            manufacturerCode = new ManufacturerCode(testManufacturerCode);
            assertEquals("test that the manufacturer code is correctly set", testManufacturerCode, manufacturerCode.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatExceptionIsThrownWhenManufacturerCodePropertiesAreCorrectlyReturned()
        throws InvalidManufacturerCodeException, InvalidBitStringException {
        String invalidManufacturerCode = "123";
        expectedException.expectMessage("Invalid Manufacturer code");
        expectedException.expect(InvalidManufacturerCodeException.class);
        manufacturerCode = new ManufacturerCode(invalidManufacturerCode);
    }

}