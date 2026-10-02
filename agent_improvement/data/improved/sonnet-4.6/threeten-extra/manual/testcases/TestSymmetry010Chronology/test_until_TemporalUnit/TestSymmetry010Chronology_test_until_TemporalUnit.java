package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link Symmetry010Date#until(java.time.temporal.Temporal, TemporalUnit)}.
 *
 * <p>Each row in {@link #data_until()} describes a (start, end, unit, expected) scenario.
 * The expected value is the signed count of complete {@code unit}s between start and end:
 * positive when end is after start, negative when end is before start, and zero when the
 * difference does not span a full unit.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_until_TemporalUnit {

    /**
     * Test data for {@link #test_until_TemporalUnit}.
     *
     * <p>Columns: year1, month1, dom1, year2, month2, dom2, unit, expected
     *
     * <p>Cases are grouped by temporal unit to make the boundary conditions obvious:
     * <ul>
     *   <li>DAYS – simple forward/backward day counts
     *   <li>WEEKS – whole-week truncation (partial week rounds toward zero)
     *   <li>MONTHS – whole-month truncation
     *   <li>YEARS – whole-year truncation
     *   <li>DECADES / CENTURIES / MILLENNIA – scaled year-based units
     *   <li>ERAS – always 0 because both dates share the CE era
     * </ul>
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // --- DAYS ---
            // Same date: zero days elapsed
            { 2014, 5, 26,  2014, 5, 26,  DAYS,      0 },
            // 9 days forward
            { 2014, 5, 26,  2014, 6,  4,  DAYS,      9 },
            // 6 days backward (negative)
            { 2014, 5, 26,  2014, 5, 20,  DAYS,     -6 },

            // --- WEEKS ---
            // Same date: zero weeks
            { 2014, 5, 26,  2014, 5, 26,  WEEKS,     0 },
            // 6 days = 0 full weeks; 7 days = 1 full week
            { 2014, 5, 26,  2014, 6,  1,  WEEKS,     1 },
            // 10 days = 1 full week (partial second week truncated)
            { 2014, 5, 26,  2014, 6,  5,  WEEKS,     1 },

            // --- MONTHS ---
            // Same date: zero months
            { 2014, 5, 26,  2014, 5, 26,  MONTHS,    0 },
            // One day short of a full month: still 0
            { 2014, 5, 26,  2014, 6, 25,  MONTHS,    0 },
            // Exactly one month later: 1
            { 2014, 5, 26,  2014, 6, 26,  MONTHS,    1 },

            // --- YEARS ---
            // Same date: zero years
            { 2014, 5, 26,  2014, 5, 26,  YEARS,     0 },
            // One day short of a full year: still 0
            { 2014, 5, 26,  2015, 5, 25,  YEARS,     0 },
            // Exactly one year later: 1
            { 2014, 5, 26,  2015, 5, 26,  YEARS,     1 },

            // --- DECADES ---
            // Same date: zero decades
            { 2014, 5, 26,  2014, 5, 26,  DECADES,   0 },
            // One day short of a full decade: still 0
            { 2014, 5, 26,  2024, 5, 25,  DECADES,   0 },
            // Exactly one decade later: 1
            { 2014, 5, 26,  2024, 5, 26,  DECADES,   1 },

            // --- CENTURIES ---
            // Same date: zero centuries
            { 2014, 5, 26,  2014, 5, 26,  CENTURIES, 0 },
            // One day short of a full century: still 0
            { 2014, 5, 26,  2114, 5, 25,  CENTURIES, 0 },
            // Exactly one century later: 1
            { 2014, 5, 26,  2114, 5, 26,  CENTURIES, 1 },

            // --- MILLENNIA ---
            // Same date: zero millennia
            { 2014, 5, 26,  2014, 5, 26,  MILLENNIA, 0 },
            // One day short of a full millennium: still 0
            { 2014, 5, 26,  3014, 5, 25,  MILLENNIA, 0 },
            // Exactly one millennium later: 1
            { 2014, 5, 26,  3014, 5, 26,  MILLENNIA, 1 },

            // --- ERAS ---
            // Both dates in the CE era: always 0
            { 2014, 5, 26,  3014, 5, 26,  ERAS,      0 },
        };
    }

    /**
     * Verifies that {@code start.until(end, unit)} returns {@code expected} complete units
     * between the two Symmetry010 dates.
     */
    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        Symmetry010Date start = Symmetry010Date.of(year1, month1, dom1);
        Symmetry010Date end   = Symmetry010Date.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
