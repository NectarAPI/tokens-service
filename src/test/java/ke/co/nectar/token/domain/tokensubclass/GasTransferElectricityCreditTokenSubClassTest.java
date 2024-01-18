package ke.co.nectar.token.domain.tokensubclass;

import ke.co.nectar.token.domain.tokensubclass.class0.GasCreditTransferTokenSubClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GasTransferElectricityCreditTokenSubClassTest {

    private GasCreditTransferTokenSubClass gasCreditTransferTokenSubClass;

    @Before
    public void setUp() throws Exception {
        gasCreditTransferTokenSubClass = new GasCreditTransferTokenSubClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 2, gasCreditTransferTokenSubClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Gas", gasCreditTransferTokenSubClass.getName());
        assertEquals("test bit string sequence", "0010", gasCreditTransferTokenSubClass.bitsToString());
    }

}