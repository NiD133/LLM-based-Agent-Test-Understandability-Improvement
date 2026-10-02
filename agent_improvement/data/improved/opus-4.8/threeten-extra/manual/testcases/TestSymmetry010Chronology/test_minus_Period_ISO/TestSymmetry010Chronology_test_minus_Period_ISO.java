package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting an ISO {@link Period} from a {@link Symmetry010Date} is rejected.
 *
 * <p>The Symmetry010 calendar does not accept ISO periods (its month lengths differ
 * from the ISO calendar), so {@code minus} with an ISO period must throw.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_minus_Period_ISO {

    @Test
    public void minus_isoPeriod_throwsDateTimeException() {
        Symmetry010Date date = Symmetry010Date.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.minus(isoPeriod));
    }
}
