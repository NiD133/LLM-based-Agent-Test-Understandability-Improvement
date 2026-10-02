package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_minusDays {

    // Maps Julian calendar dates to their equivalent ISO (LocalDate) representations.
    // Used to verify that subtracting days from a JulianDate yields the correct ISO result.
    public static Object[][] data_samples() {
        return new Object[][] {
            { JulianDate.of(1,    1,  1),  LocalDate.of(   0, 12, 30) },
            { JulianDate.of(1,    1,  2),  LocalDate.of(   0, 12, 31) },
            { JulianDate.of(1,    1,  3),  LocalDate.of(   1,  1,  1) },
            { JulianDate.of(1,    2, 28),  LocalDate.of(   1,  2, 26) },
            { JulianDate.of(1,    3,  1),  LocalDate.of(   1,  2, 27) },
            { JulianDate.of(1,    3,  2),  LocalDate.of(   1,  2, 28) },
            { JulianDate.of(1,    3,  3),  LocalDate.of(   1,  3,  1) },
            // Year 4 is a Julian leap year (Feb has 29 days)
            { JulianDate.of(4,    2, 28),  LocalDate.of(   4,  2, 26) },
            { JulianDate.of(4,    2, 29),  LocalDate.of(   4,  2, 27) },
            { JulianDate.of(4,    3,  1),  LocalDate.of(   4,  2, 28) },
            { JulianDate.of(4,    3,  2),  LocalDate.of(   4,  2, 29) },
            { JulianDate.of(4,    3,  3),  LocalDate.of(   4,  3,  1) },
            // Year 100 is a Julian leap year (unlike Gregorian)
            { JulianDate.of(100,  2, 28),  LocalDate.of( 100,  2, 26) },
            { JulianDate.of(100,  2, 29),  LocalDate.of( 100,  2, 27) },
            { JulianDate.of(100,  3,  1),  LocalDate.of( 100,  2, 28) },
            { JulianDate.of(100,  3,  2),  LocalDate.of( 100,  3,  1) },
            { JulianDate.of(100,  3,  3),  LocalDate.of( 100,  3,  2) },
            // Year 0 (1 BC in proleptic Julian)
            { JulianDate.of(0,   12, 31),  LocalDate.of(   0, 12, 29) },
            { JulianDate.of(0,   12, 30),  LocalDate.of(   0, 12, 28) },
            // Around the Gregorian calendar reform (1582)
            { JulianDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },
            // Modern dates
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(JulianDate julian, LocalDate iso) {
        assertEquals(iso,             LocalDate.from(julian.minus(  0, DAYS)));
        assertEquals(iso.minusDays(1),  LocalDate.from(julian.minus(  1, DAYS)));
        assertEquals(iso.minusDays(35), LocalDate.from(julian.minus( 35, DAYS)));
        assertEquals(iso.minusDays(-1), LocalDate.from(julian.minus( -1, DAYS)));
        assertEquals(iso.minusDays(-60),LocalDate.from(julian.minus(-60, DAYS)));
    }
}
