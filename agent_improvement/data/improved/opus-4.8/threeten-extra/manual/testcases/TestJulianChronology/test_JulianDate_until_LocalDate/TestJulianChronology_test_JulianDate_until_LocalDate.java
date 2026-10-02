package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianDate#until(java.time.temporal.Temporal)} returns a zero
 * period when measured against the ISO {@link LocalDate} that denotes the same day.
 */
public class TestJulianChronology_test_JulianDate_until_LocalDate {

    /**
     * Pairs of dates that refer to the exact same day on the time-line, expressed
     * once as a Julian date and once as the equivalent ISO {@link LocalDate}.
     * <p>
     * For example, {@code 0001-01-01 (Julian)} is {@code 0000-12-30 (ISO)}.
     *
     * @return rows of {@code { julianDate, equivalentIsoDate }}
     */
    public static Object[][] equivalentJulianAndIsoDates() {
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
    @MethodSource("equivalentJulianAndIsoDates")
    public void until_equivalentIsoDate_returnsZeroPeriod(JulianDate julian, LocalDate equivalentIso) {
        assertEquals(JulianChronology.INSTANCE.period(0, 0, 0), julian.until(equivalentIso));
    }
}
