package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.tokenclass.class0.ElectricityCreditTransferTokenClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElectricityCreditTransferTokenClassTest {

    private ElectricityCreditTransferTokenClass electricityCreditTransferTokenClass;

    @Before
    public void setUp() throws InvalidRangeException {
        electricityCreditTransferTokenClass = new ElectricityCreditTransferTokenClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 0, electricityCreditTransferTokenClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Electricity Credit Transfer", electricityCreditTransferTokenClass.getName());
        assertEquals("test bit string sequence", "00", electricityCreditTransferTokenClass.bitsToString());
    }
}