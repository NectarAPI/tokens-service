package ke.co.nectar.token.domain.range;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateRangeTest {

    private DateRange dateRange ;
    private DateTime startTime = new DateTime(1993, 1, 1, 0, 0, 0, 0);
    private DateTime endTime = new DateTime(2023, 11, 24, 0, 0, 0, 0);
    private final int NO_STEPS = 400;

    @Before
    public void setUp() {
        dateRange = new DateRange(startTime, NO_STEPS, endTime) ;
    }

    @After
    public void tearDown() {
        dateRange = null ;
    }

    @Test
    public void testThatCorrectDateRangePropertiesAreAdded() {
        assertEquals("assert that start date is correct", startTime, dateRange.getStartRange());
        assertEquals("assert that end date is correct", endTime, dateRange.getEndRange());
        assertEquals("test that the step increases are correct", NO_STEPS, dateRange.getNoOfSteps());
        assertEquals("test that the correct number of durations are created", NO_STEPS, dateRange.getIntervals().size());
    }

    @Test
    public void testThatTheCorrectIntervalsAreReturned() {
        long noOfMillisPerInterval = dateRange.getMillisPerInterval() ;
        assertEquals("test that the first interval is correct", dateRange.getStep(0), startTime);
        assertEquals("test that the second interval is correct", dateRange.getStep(1), startTime.plus(noOfMillisPerInterval));
        assertEquals("test that the last interval is correct",  endTime, dateRange.getStep(dateRange.getNoOfSteps()-1).plus(noOfMillisPerInterval));
    }

}