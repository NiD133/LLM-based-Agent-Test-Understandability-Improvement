package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianChronology#dateEpochDay(long)} reconstructs the correct
 * {@link JulianDate} from an epoch-day value.
 * <p>
 * The Julian and ISO calendars share the same epoch-day numbering (days since
 * 1970-01-01), but label that same day differently. Each test case therefore pairs
 * a {@code JulianDate} with the ISO {@link LocalDate} that falls on the very same day.
 */
public class TestJulianChronology_test_JulianDate_chronology_dateEpochDay {

    /**
     * Pairs of dates that refer to the same physical day: the expected Julian date
     * and the equivalent ISO date used to source the epoch-day under test.
     */
    public static Object[][] data_sameDayJulianAndIso() {
        return new Object[][] {
            { JulianDate.of(1, 1, 1), LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2), LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3), LocalDate.of(1, 1, 1) },
            { JulianDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
            { JulianDate.of(1, 3, 1), LocalDate.of(1, 2, 27) },
            { JulianDate.of(1, 3, 2), LocalDate.of(1, 2, 28) },
            { JulianDate.of(1, 3, 3), LocalDate.of(1, 3, 1) },
            { JulianDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { JulianDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { JulianDate.of(4, 3, 1), LocalDate.of(4, 2, 28) },
            { JulianDate.of(4, 3, 2), LocalDate.of(4, 2, 29) },
            { JulianDate.of(4, 3, 3), LocalDate.of(4, 3, 1) },
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3, 1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3, 2), LocalDate.of(100, 3, 1) },
            { JulianDate.of(100, 3, 3), LocalDate.of(100, 3, 2) },
            { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012, 6, 22), LocalDate.of(2012, 7, 5) },
            { JulianDate.of(2012, 6, 23), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_sameDayJulianAndIso")
    public void dateEpochDay_returnsJulianDateForSameDayEpochDay(JulianDate expectedJulian, LocalDate sameDayIso) {
        long epochDay = sameDayIso.toEpochDay();

        assertEquals(expectedJulian, JulianChronology.INSTANCE.dateEpochDay(epochDay));
    }
}
