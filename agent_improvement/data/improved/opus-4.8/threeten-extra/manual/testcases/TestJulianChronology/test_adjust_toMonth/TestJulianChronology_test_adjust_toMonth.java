package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link JulianDate} cannot be adjusted with an ISO {@link Month}.
 */
public class TestJulianChronology_test_adjust_toMonth {

    /**
     * Adjusting a Julian date with an ISO {@code Month} is unsupported, because
     * {@code Month} belongs to the ISO calendar system rather than the Julian one,
     * so the adjustment must fail with a {@link DateTimeException}.
     */
    @Test
    public void test_adjust_toMonth() {
        JulianDate julianDate = JulianDate.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> julianDate.with(Month.APRIL));
    }
}
