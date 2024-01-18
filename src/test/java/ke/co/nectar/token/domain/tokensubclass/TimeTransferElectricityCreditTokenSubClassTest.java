package ke.co.nectar.token.domain.tokensubclass;

import ke.co.nectar.token.domain.tokensubclass.class0.TimeTokenSubClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeTransferElectricityCreditTokenSubClassTest {

    private TimeTokenSubClass timeTokenSubClass;

    @Before
    public void setUp() throws Exception {
        timeTokenSubClass = new TimeTokenSubClass();
    }

    @Test
    public void testThatThePropertiesOfCreditTransferAreSet() {
        assertEquals("test that bit string is correct", 3, timeTokenSubClass.getBitString().getValue());
        assertEquals("test that the correct name is set", "Time", timeTokenSubClass.getName());
        assertEquals("test bit string sequence", "0011", timeTokenSubClass.bitsToString());
    }

}