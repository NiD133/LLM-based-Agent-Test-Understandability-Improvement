package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting an ISO Period from a Symmetry454Date throws DateTimeException,
 * because ISO periods are incompatible with the Symmetry454 calendar system.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_Period_ISO {

    @Test
    public void test_minus_Period_ISO() {
        // Period.ofMonths is an ISO period; subtracting it from a Symmetry454Date must fail
        assertThrows(DateTimeException.class,
                () -> Symmetry454Date.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}
