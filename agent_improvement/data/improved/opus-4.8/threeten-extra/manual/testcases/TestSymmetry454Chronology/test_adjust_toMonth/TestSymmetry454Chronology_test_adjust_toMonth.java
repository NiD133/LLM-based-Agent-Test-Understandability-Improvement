package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that adjusting a {@link Symmetry454Date} with an ISO {@link Month}
 * is rejected.
 *
 * <p>A {@code java.time.Month} is an ISO-calendar adjuster, so applying it to a
 * Symmetry454 date is unsupported and must raise a {@link DateTimeException}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_adjust_toMonth {

    @Test
    public void test_adjust_toMonth() {
        Symmetry454Date date = Symmetry454Date.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
