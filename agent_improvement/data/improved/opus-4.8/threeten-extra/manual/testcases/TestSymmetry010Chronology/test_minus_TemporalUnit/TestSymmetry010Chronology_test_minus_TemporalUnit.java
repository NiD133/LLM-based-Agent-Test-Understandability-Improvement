package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry010Date#minus(long, TemporalUnit)}.
 * <p>
 * Each case subtracts {@code amount} of {@code unit} from a start date and
 * checks the resulting date.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_minus_TemporalUnit {

    /**
     * Cases for {@link #test_minus_TemporalUnit}.
     * <p>
     * Each row is laid out in the order the test reads it:
     * {@code { startYear, startMonth, startDom, amount, unit, expectedYear, expectedMonth, expectedDom } },
     * asserting that {@code Symmetry010Date.of(start).minus(amount, unit)} equals
     * {@code Symmetry010Date.of(expected)}.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            // unit = DAYS
            { 2014, 5, 26, 0, DAYS, 2014, 5, 26 },
            { 2014, 6, 3, 8, DAYS, 2014, 5, 26 },
            { 2014, 5, 23, -3, DAYS, 2014, 5, 26 },

            // unit = WEEKS
            { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 },
            { 2014, 6, 16, 3, WEEKS, 2014, 5, 26 },
            { 2014, 4, 21, -5, WEEKS, 2014, 5, 26 },

            // unit = MONTHS
            { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 },
            { 2014, 8, 26, 3, MONTHS, 2014, 5, 26 },
            { 2013, 12, 26, -5, MONTHS, 2014, 5, 26 },

            // unit = YEARS
            { 2014, 5, 26, 0, YEARS, 2014, 5, 26 },
            { 2017, 5, 26, 3, YEARS, 2014, 5, 26 },
            { 2009, 5, 26, -5, YEARS, 2014, 5, 26 },

            // unit = DECADES
            { 2014, 5, 26, 0, DECADES, 2014, 5, 26 },
            { 2044, 5, 26, 3, DECADES, 2014, 5, 26 },
            { 1964, 5, 26, -5, DECADES, 2014, 5, 26 },

            // unit = CENTURIES
            { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 },
            { 2314, 5, 26, 3, CENTURIES, 2014, 5, 26 },
            { 1514, 5, 26, -5, CENTURIES, 2014, 5, 26 },

            // unit = MILLENNIA
            { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 },
            { 5014, 5, 26, 3, MILLENNIA, 2014, 5, 26 },
            { 2014 - 1000, 5, 26, -1, MILLENNIA, 2014, 5, 26 },

            // WEEKS crossing month and year boundaries
            { 2015, 1, 17, 3, WEEKS, 2014, 12, 26 },
            { 2013, 12, 21, -5, WEEKS, 2014, 1, 26 },
            { 2012, 7, 17, 3, WEEKS, 2012, 6, 26 },
            { 2012, 6, 21, -5, WEEKS, 2012, 7, 26 },
            { 2013, 6, 28, 52 + 1, WEEKS, 2012, 6, 21 },
            { 2019, 6, 21, 6 * 52 + 1, WEEKS, 2013, 6, 21 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_TemporalUnit(int startYear, int startMonth, int startDom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        Symmetry010Date start = Symmetry010Date.of(startYear, startMonth, startDom);
        Symmetry010Date expected = Symmetry010Date.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.minus(amount, unit));
    }
}
