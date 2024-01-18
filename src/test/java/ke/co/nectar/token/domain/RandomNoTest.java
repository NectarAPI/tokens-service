package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomNoTest {

    private RandomNo random;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testThatSetPropertiesFromBitStringAreCorrect() {
        try {
            BitString rndBitString = new BitString(0x9L);
            rndBitString.setLength(4);
            random = new RandomNo(rndBitString);
            assertEquals("test that returned bitstring is correct", rndBitString, random.getBitString());
            assertEquals("test that the correct bit string representation is returned", "1001", random.bitsToString());
        } catch (InvalidRangeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatRandomBitsFromTimestampAreGenerated()
        throws InvalidRangeException {
        BitString rndBitString = new BitString(0x9L);
        rndBitString.setLength(4);
        random = new RandomNo(rndBitString);
        System.out.printf("Generated random bits: %d\n", random.getBitString().getValue());
        assertTrue("test that random bits are generated", null != random &&
                                                            random.getBitString().getValue() >= 0 &&
                                                            random.getBitString().getValue() <= 15);
    }

    @Test
    public void testThatRNDValueIsExtracted() {
        try {
            BitString rndBitString = new BitString(0x9L);
            rndBitString.setLength(4);
            assertEquals("test that correct random value is required", 9, RandomNo.getRNDNo(rndBitString).getBitString().getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}