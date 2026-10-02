package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests that {@link Symmetry454Date#of} rejects the leap-day date (December 29)
 * for years that are not Symmetry454 leap years.
 * <p>
 * In the Symmetry454 calendar a leap year gains an extra "leap week", which
 * extends December to 35 days. Only then does a 29th of December exist. The
 * years below are all non-leap years, so {@code of(year, 12, 29)} must fail.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_badLeapDayDates {

    @ParameterizedTest
    @ValueSource(ints = { 1, 100, 200, 2000 })
    public void test_badLeapDayDates(int nonLeapYear) {
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(nonLeapYear, 12, 29));
    }
}
