package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.tokenclass.class0.WaterCreditTransferTokenClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WaterCreditTransferTokenClassTest {

    private WaterCreditTransferTokenClass waterCreditTransferTokenClass;

    @Before
    public void setUp() throws InvalidRangeException {
        waterCreditTransferTokenClass = new WaterCreditTransferTokenClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 0, waterCreditTransferTokenClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Water Credit Transfer", waterCreditTransferTokenClass.getName());
        assertEquals("test bit string sequence", "00", waterCreditTransferTokenClass.bitsToString());
    }
}