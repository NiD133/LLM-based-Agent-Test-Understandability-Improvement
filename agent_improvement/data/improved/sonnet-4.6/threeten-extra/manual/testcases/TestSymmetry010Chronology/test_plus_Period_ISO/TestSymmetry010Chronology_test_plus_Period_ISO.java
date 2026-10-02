package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that adding an ISO {@link Period} to a {@link Symmetry010Date} is rejected,
 * because ISO periods are incompatible with the Symmetry010 calendar system.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_Period_ISO {

    @Test
    public void test_plus_Period_ISO() {
        // Period.ofMonths(2) is an ISO period; Symmetry010Date.plus() must reject it.
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}
