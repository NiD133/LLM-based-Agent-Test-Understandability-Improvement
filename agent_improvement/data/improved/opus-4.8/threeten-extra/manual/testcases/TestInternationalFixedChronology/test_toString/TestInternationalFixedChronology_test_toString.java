package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the human-readable {@link InternationalFixedDate#toString()} representation.
 * <p>
 * The expected format is {@code "Ifc <era> <year>/<month>/<day>"}, where the month
 * and day are zero-padded to two digits.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_toString {

    @Test
    public void toString_startOfFirstYear() {
        // Single-digit year is NOT padded; month and day are padded to two digits.
        assertEquals("Ifc CE 1/01/01", InternationalFixedDate.of(1, 1, 1).toString());
    }

    @Test
    public void toString_ordinaryDate() {
        assertEquals("Ifc CE 2012/06/23", InternationalFixedDate.of(2012, 6, 23).toString());
    }

    @Test
    public void toString_yearDay_inFirstYear() {
        // Month 13, day 29 is the "Year Day".
        assertEquals("Ifc CE 1/13/29", InternationalFixedDate.of(1, 13, 29).toString());
    }

    @Test
    public void toString_leapDay() {
        // Month 6, day 29 in a leap year is the "Leap Day".
        assertEquals("Ifc CE 2012/06/29", InternationalFixedDate.of(2012, 6, 29).toString());
    }

    @Test
    public void toString_yearDay_inLeapYear() {
        assertEquals("Ifc CE 2012/13/29", InternationalFixedDate.of(2012, 13, 29).toString());
    }
}
