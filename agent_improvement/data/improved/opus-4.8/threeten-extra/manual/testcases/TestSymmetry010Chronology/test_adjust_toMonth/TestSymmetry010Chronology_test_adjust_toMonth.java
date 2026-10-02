package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link Symmetry010Date} cannot be adjusted using a plain ISO
 * {@link Month}.
 * <p>
 * A {@code java.time.Month} is a temporal adjuster that sets the
 * {@code MONTH_OF_YEAR} field on an ISO-based date. It is not a valid adjuster
 * for the Symmetry010 chronology, so applying it must fail.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_adjust_toMonth {

    @Test
    public void adjustingWithIsoMonth_throwsDateTimeException() {
        Symmetry010Date date = Symmetry010Date.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
