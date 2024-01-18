package ke.co.nectar.token.domain.range;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitsValueRangeTest {

    private UnitsValueRange unitsValueRange ;
    private Double unitsRangeStart = 30d;
    private Double unitsRangeEnd = 16666d;
    private final int NO_OF_STEPS = 100;

    @Before
    public void setUp() {
        unitsValueRange = new UnitsValueRange(unitsRangeStart, NO_OF_STEPS, unitsRangeEnd) ;
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testThatUnitsValueRangePropertiesAreCorrect() {
        assertEquals("test that correct units start range are set", unitsRangeStart, unitsValueRange.getStartRange());
        assertEquals("test that correct units end range are set", unitsRangeEnd, unitsValueRange.getEndRange());
        assertEquals("test that correct number of units are set", NO_OF_STEPS, unitsValueRange.getNoOfSteps());
    }

    @Test
    public void testThatTheCorrectNoOfStepsAreReturned() {
        assertEquals("test the first step is correct", (Double) 30d, unitsValueRange.getStep(0));
        assertEquals("test that the second step is correct", 195, (int) Math.ceil(unitsValueRange.getStep(1)));
    }
}