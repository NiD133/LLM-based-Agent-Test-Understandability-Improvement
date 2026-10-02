package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a Symmetry010Date rejects adjustment via a java.time.Month,
 * because Month is an ISO-specific type incompatible with the Symmetry010 calendar.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_adjust_toMonth {

    @Test
    public void test_adjust_toMonth() {
        // Symmetry010Date.with(Month) must throw because Month is ISO-specific
        // and cannot be applied to a non-ISO chronology date.
        Symmetry010Date date = Symmetry010Date.of(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
