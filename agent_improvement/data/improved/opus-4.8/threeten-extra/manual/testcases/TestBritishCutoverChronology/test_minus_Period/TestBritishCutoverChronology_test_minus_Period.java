package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting a {@link java.time.chrono.ChronoPeriod} from a
 * {@link BritishCutoverDate} produces the expected date.
 */
public class TestBritishCutoverChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtracting 1 month from 1752-10-12 lands in the cutover month and yields 1752-09-23.
        BritishCutoverDate startInCutoverMonth = BritishCutoverDate.of(1752, 10, 12);
        BritishCutoverDate expectedInCutoverMonth = BritishCutoverDate.of(1752, 9, 23);
        assertEquals(expectedInCutoverMonth,
                startInCutoverMonth.minus(BritishCutoverChronology.INSTANCE.period(0, 1, 0)));

        // Subtracting 2 months and 3 days from 2014-05-26 yields 2014-03-23.
        BritishCutoverDate startModern = BritishCutoverDate.of(2014, 5, 26);
        BritishCutoverDate expectedModern = BritishCutoverDate.of(2014, 3, 23);
        assertEquals(expectedModern,
                startModern.minus(BritishCutoverChronology.INSTANCE.period(0, 2, 3)));
    }
}
