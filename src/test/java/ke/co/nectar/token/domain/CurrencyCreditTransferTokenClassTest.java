package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.tokenclass.class0.CurrencyCreditTransferTokenClass;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyCreditTransferTokenClassTest {

    private CurrencyCreditTransferTokenClass currencyCreditTransferTokenClass;

    @Before
    public void setUp() throws InvalidRangeException {
        currencyCreditTransferTokenClass = new CurrencyCreditTransferTokenClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 0, currencyCreditTransferTokenClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Currency Credit Transfer", currencyCreditTransferTokenClass.getName());
        assertEquals("test bit string sequence", "00", currencyCreditTransferTokenClass.bitsToString());
    }
}