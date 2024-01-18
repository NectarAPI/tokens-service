package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.generators.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AmountTest {

    private Amount amount ;

    @Before
    public void setUp() throws Exception {
        final double UNITS_PURCHASED = 25.6;
        amount = new Amount(UNITS_PURCHASED) ;
    }

    @After
    public void tearDown() throws Exception {
        amount = null ;
    }

    @Test
    public void testThatCorrectLengthIsReturned() {
        assertEquals("test that the correct number of bits is returned", 16, amount.getLength());
    }



    @Test
    public void testThatTheCorrectAmountNameIsCreated() {
        assertEquals("check that the correct name is returned", "Amount", amount.getName());
    }

    @Test
    public void testThatSetAmountPurchasedAmountReturnsCorrectValue() {
        assertTrue("test that the correct purchased amout is returned", amount.getAmountPurchased() == 25.6);
    }

    @Test
    public void testThatTheCorrectBitStringIsGenerated() {
        assertEquals("test that the generated bitstring is correct", "0000000100000000", amount.toString());
    }

    @Test
    public void testConversionFromBitStringToAmount() {
        try {
            final int NO_OF_BITS = 16;
            BitString unitsPurchasedBitString = new BitString(0x100L);
            unitsPurchasedBitString.setLength(NO_OF_BITS);
            double unitsPurchased = Utils.convertToDouble(unitsPurchasedBitString);
            assertTrue("test that units purchased are correctly returned", unitsPurchased == 25.6);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}