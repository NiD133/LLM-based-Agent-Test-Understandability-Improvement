package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_adjust_toLocalDate {

    /**
     * Verifies that adjusting an InternationalFixedDate via a ISO LocalDate
     * converts the ISO date into the correct IFC date.
     *
     * ISO 2012-07-06 falls on IFC 2012/07/19 because each IFC month is 28
     * days and the calendar starts on the same epoch day as ISO.
     */
    @Test
    public void test_adjust_toLocalDate() {
        InternationalFixedDate fixed = InternationalFixedDate.of(2000, 1, 4);
        InternationalFixedDate test = fixed.with(LocalDate.of(2012, 7, 6));
        assertEquals(InternationalFixedDate.of(2012, 7, 19), test);
    }
}
