package ke.co.nectar.token.domain.tokensubclass;

import ke.co.nectar.token.domain.tokensubclass.class0.ElectricityCurrencyCreditTransferTokenSubClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyTransferElectricityCreditTokenSubClassTest {

    private ElectricityCurrencyCreditTransferTokenSubClass electricityCurrencyCreditTransferTokenSubClass;

    @Before
    public void setUp() throws Exception {
        electricityCurrencyCreditTransferTokenSubClass = new ElectricityCurrencyCreditTransferTokenSubClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 4, electricityCurrencyCreditTransferTokenSubClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Electricity Currency", electricityCurrencyCreditTransferTokenSubClass.getName());
        assertEquals("test bit string sequence", "0100", electricityCurrencyCreditTransferTokenSubClass.bitsToString());
    }
}