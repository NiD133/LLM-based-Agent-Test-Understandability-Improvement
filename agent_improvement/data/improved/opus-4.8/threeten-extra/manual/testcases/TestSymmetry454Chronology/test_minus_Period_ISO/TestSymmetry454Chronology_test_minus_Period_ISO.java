package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting an ISO {@link Period} from a {@link Symmetry454Date} is rejected.
 *
 * <p>An ISO {@code Period} belongs to the ISO calendar system, so it cannot be applied to a
 * Symmetry454 date. The operation is expected to fail with a {@link DateTimeException} rather
 * than silently converting between calendar systems.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_Period_ISO {

    @Test
    public void test_minus_Period_ISO() {
        Symmetry454Date date = Symmetry454Date.of(2014, 5, 26);

        assertThrows(DateTimeException.class, () -> date.minus(Period.ofMonths(2)));
    }
}
