package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link JulianDate} rejects arithmetic with an ISO-based {@link Period}.
 */
public class TestJulianChronology_test_plus_Period_ISO {

    /**
     * A {@code Period} is expressed in the ISO calendar system, so its months and years
     * are not interoperable with the Julian chronology. Adding one to a {@link JulianDate}
     * must therefore fail with a {@link DateTimeException} rather than silently mixing
     * calendar systems.
     */
    @Test
    public void test_plus_Period_ISO() {
        JulianDate julianDate = JulianDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> julianDate.plus(isoPeriod));
    }
}
