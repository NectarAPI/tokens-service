package ke.co.nectar.token.domain.range;

import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.base.BitString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomValueRangeTest {

    private RandomValueRange randomValueRange;
    private RandomNo startRandomValueRange ;
    private RandomNo endRandomValueRange  ;
    private final int RANDOM_VALUE_STEPS = 3;

    @Before
    public void setUp() throws Exception {
        BitString initialBitString = new BitString(0x0L) ;
        initialBitString.setLength(4);
        BitString endBitString = new BitString(0xFL) ;
        endBitString.setLength(4);
        startRandomValueRange = new RandomNo(initialBitString);
        endRandomValueRange = new RandomNo(endBitString) ;
        randomValueRange = new RandomValueRange(startRandomValueRange, RANDOM_VALUE_STEPS, endRandomValueRange);
    }

    @After
    public void tearDown() {
        randomValueRange = null ;
    }

    @Test
    public void testThatRandomValueRangeValuesAreCorrect() {
        assertEquals("test that the correct start range value is correct", startRandomValueRange, randomValueRange.getStartRange());
        assertEquals("test that the correct end range value is correct", endRandomValueRange, randomValueRange.getEndRange());
        assertEquals("test that the calculated interval per range", 5, randomValueRange.getDiffPerSteps());
    }

}