package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link LocalDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero period when the target {@link JulianDate} denotes the very same point on
 * the time-line as the ISO date.
 * <p>
 * Each sample pairs a {@code JulianDate} with the ISO {@code LocalDate} that
 * represents the identical day (for example, {@code 0001-01-01 (Julian)} is
 * {@code 0000-12-30 (ISO)}). Because the two values are the same day, the period
 * between them must be {@link Period#ZERO}.
 */
public class TestJulianChronology_test_LocalDate_until_JulianDate {

    /**
     * Provides Julian/ISO pairs that refer to the same day on the time-line.
     *
     * @return rows of {@code { equivalentJulianDate, equivalentIsoDate }}
     */
    public static Object[][] data_equivalentJulianAndIsoDates() {
        return new Object[][] {
            { JulianDate.of(1, 1, 1),       LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),       LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),       LocalDate.of(1, 1, 1) },
            { JulianDate.of(1, 2, 28),      LocalDate.of(1, 2, 26) },
            { JulianDate.of(1, 3, 1),       LocalDate.of(1, 2, 27) },
            { JulianDate.of(1, 3, 2),       LocalDate.of(1, 2, 28) },
            { JulianDate.of(1, 3, 3),       LocalDate.of(1, 3, 1) },
            { JulianDate.of(4, 2, 28),      LocalDate.of(4, 2, 26) },
            { JulianDate.of(4, 2, 29),      LocalDate.of(4, 2, 27) },
            { JulianDate.of(4, 3, 1),       LocalDate.of(4, 2, 28) },
            { JulianDate.of(4, 3, 2),       LocalDate.of(4, 2, 29) },
            { JulianDate.of(4, 3, 3),       LocalDate.of(4, 3, 1) },
            { JulianDate.of(100, 2, 28),    LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29),    LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3, 1),     LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3, 2),     LocalDate.of(100, 3, 1) },
            { JulianDate.of(100, 3, 3),     LocalDate.of(100, 3, 2) },
            { JulianDate.of(0, 12, 31),     LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30),     LocalDate.of(0, 12, 28) },
            { JulianDate.of(1582, 10, 4),   LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5),   LocalDate.of(1582, 10, 15) },
            { JulianDate.of(1945, 10, 30),  LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012, 6, 22),   LocalDate.of(2012, 7, 5) },
            { JulianDate.of(2012, 6, 23),   LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentJulianAndIsoDates")
    public void test_LocalDate_until_JulianDate(JulianDate equivalentJulianDate, LocalDate equivalentIsoDate) {
        // Same day on the time-line, so the gap between them is empty.
        assertEquals(Period.ZERO, equivalentIsoDate.until(equivalentJulianDate));
    }
}
