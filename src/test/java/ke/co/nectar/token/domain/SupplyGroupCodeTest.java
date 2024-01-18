package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.exceptions.InvalidSupplyGroupCodeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SupplyGroupCodeTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatSupplyGroupTestPropertiesAreCorrectlyReturned() {
        try {
            String testSupplyGroupCode = "000201";
            SupplyGroupCode supplyGroupCode = new SupplyGroupCode(testSupplyGroupCode);
            assertEquals("test that the returned supply group code is returned", testSupplyGroupCode, supplyGroupCode.getValue());
            assertEquals("test that the correct name of the elem is returned", "Supply Group Code", supplyGroupCode.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidSupplyGroupCodeReturnsException()
        throws InvalidSupplyGroupCodeException {
        String testSupplyGroupCode = "0a0201";
        expectedException.expect(InvalidSupplyGroupCodeException.class);
        expectedException.expectMessage("Invalid Supply Group Code");
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode(testSupplyGroupCode);
    }

}