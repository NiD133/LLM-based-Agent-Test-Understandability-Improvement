package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_LocalDate_from_JulianDate {

    // Maps representative Julian calendar dates to their expected ISO (Gregorian) equivalents.
    // The Julian calendar runs 2 days behind ISO in year 1 and diverges further over centuries
    // because Julian leap years (every 4 years) differ from ISO leap years.
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 AD: Julian starts 2 days before ISO epoch
            { JulianDate.of(1,    1,  1),  LocalDate.of(   0, 12, 30) },
            { JulianDate.of(1,    1,  2),  LocalDate.of(   0, 12, 31) },
            { JulianDate.of(1,    1,  3),  LocalDate.of(   1,  1,  1) },
            { JulianDate.of(1,    2, 28),  LocalDate.of(   1,  2, 26) },
            { JulianDate.of(1,    3,  1),  LocalDate.of(   1,  2, 27) },
            { JulianDate.of(1,    3,  2),  LocalDate.of(   1,  2, 28) },
            { JulianDate.of(1,    3,  3),  LocalDate.of(   1,  3,  1) },
            // Year 4: both calendars have a leap day, offset stays at 2
            { JulianDate.of(4,    2, 28),  LocalDate.of(   4,  2, 26) },
            { JulianDate.of(4,    2, 29),  LocalDate.of(   4,  2, 27) },
            { JulianDate.of(4,    3,  1),  LocalDate.of(   4,  2, 28) },
            { JulianDate.of(4,    3,  2),  LocalDate.of(   4,  2, 29) },
            { JulianDate.of(4,    3,  3),  LocalDate.of(   4,  3,  1) },
            // Year 100: Julian has a leap day but ISO does not, offset grows to 3
            { JulianDate.of(100,  2, 28),  LocalDate.of( 100,  2, 26) },
            { JulianDate.of(100,  2, 29),  LocalDate.of( 100,  2, 27) },
            { JulianDate.of(100,  3,  1),  LocalDate.of( 100,  2, 28) },
            { JulianDate.of(100,  3,  2),  LocalDate.of( 100,  3,  1) },
            { JulianDate.of(100,  3,  3),  LocalDate.of( 100,  3,  2) },
            // Year 0 (1 BC): dates just before the epoch
            { JulianDate.of(0,   12, 31),  LocalDate.of(   0, 12, 29) },
            { JulianDate.of(0,   12, 30),  LocalDate.of(   0, 12, 28) },
            // Gregorian reform boundary (October 1582): offset is 10 days
            { JulianDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },
            // Modern dates: offset has grown to 13 days by the 20th century
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_JulianDate(JulianDate julian, LocalDate iso) {
        assertEquals(iso, LocalDate.from(julian));
    }
}
