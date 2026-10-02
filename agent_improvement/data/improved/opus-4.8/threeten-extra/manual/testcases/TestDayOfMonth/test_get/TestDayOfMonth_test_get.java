package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests how {@link DayOfMonth} reports its day-of-month value, both through the
 * {@code now(...)} factory methods and through the generic {@code get(TemporalField)} accessor.
 */
public class TestDayOfMonth_test_get {

    /** A fixed instance representing the 12th day of the month. */
    private static final DayOfMonth TWELFTH = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_usesCurrentDayInDefaultZone() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_usesCurrentDayInGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // get(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void get_dayOfMonthField_returnsTheDayValue() {
        assertEquals(12, TWELFTH.get(DAY_OF_MONTH));
    }
}
