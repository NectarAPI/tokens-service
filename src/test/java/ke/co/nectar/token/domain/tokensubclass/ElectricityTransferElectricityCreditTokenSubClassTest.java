package ke.co.nectar.token.domain.tokensubclass;

import ke.co.nectar.token.domain.tokensubclass.class0.ElectricityCreditTransferTokenSubClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElectricityTransferElectricityCreditTokenSubClassTest {

    private ElectricityCreditTransferTokenSubClass electricityCreditTransferTokenSubClass;

    @Before
    public void setUp() throws Exception {
        electricityCreditTransferTokenSubClass = new ElectricityCreditTransferTokenSubClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 0, electricityCreditTransferTokenSubClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Electricity", electricityCreditTransferTokenSubClass.getName());
        assertEquals("test bit string sequence", "0000", electricityCreditTransferTokenSubClass.bitsToString());
    }

}