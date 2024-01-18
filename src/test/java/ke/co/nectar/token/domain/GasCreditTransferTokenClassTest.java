package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.tokenclass.class0.GasCreditTransferTokenClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GasCreditTransferTokenClassTest {

    private GasCreditTransferTokenClass gasCreditTransferTokenClass;

    @Before
    public void setUp() throws InvalidRangeException {
        gasCreditTransferTokenClass = new GasCreditTransferTokenClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 0, gasCreditTransferTokenClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Gas Credit Transfer", gasCreditTransferTokenClass.getName());
        assertEquals("test bit string sequence", "00", gasCreditTransferTokenClass.bitsToString());
    }
}