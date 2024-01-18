package ke.co.nectar.token.domain.tokensubclass;

import ke.co.nectar.token.domain.tokensubclass.class0.WaterCreditTransferTokenSubClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WaterTransferElectricityCreditTokenSubClassTest {

    private WaterCreditTransferTokenSubClass waterCreditTransferTokenSubClass;

    @Before
    public void setUp() throws Exception {
        waterCreditTransferTokenSubClass = new WaterCreditTransferTokenSubClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 1, waterCreditTransferTokenSubClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Water", waterCreditTransferTokenSubClass.getName());
        assertEquals("test bit string sequence", "0001", waterCreditTransferTokenSubClass.bitsToString());
    }
}