package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_minus_Period_ISO {

    /**
     * Subtracting an ISO {@link Period} from a {@link Symmetry010Date} must throw
     * {@link DateTimeException} because an ISO-chronology period is incompatible
     * with the Symmetry010 calendar system.
     */
    @Test
    public void test_minus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}
