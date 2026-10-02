package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#from(java.time.temporal.TemporalAmount)} when the source
 * amount is expressed in weeks and days.
 */
public class TestDays_test_from_P2W {

    @Test
    public void from_convertsWeeksAndDaysToDays() {
        // A temporal amount of 2 weeks and 0 days should convert to 2 * 7 = 14 days.
        Days expected = Days.of(14);
        Days actual = Days.from(new MockWeeksDays(2, 0));

        assertEquals(expected, actual);
    }
}
