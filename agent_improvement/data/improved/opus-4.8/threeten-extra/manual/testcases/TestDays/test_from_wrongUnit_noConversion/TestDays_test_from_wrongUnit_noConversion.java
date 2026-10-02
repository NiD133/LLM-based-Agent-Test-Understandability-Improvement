package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#from(java.time.temporal.TemporalAmount)} rejects a
 * temporal amount whose unit cannot be converted to whole days.
 */
public class TestDays_test_from_wrongUnit_noConversion {

    /**
     * A {@code Period} expressed in months has no clean conversion to days,
     * so {@code Days.from} must fail with a {@link DateTimeException}.
     */
    @Test
    public void test_from_wrongUnit_noConversion() {
        assertThrows(DateTimeException.class, () -> Days.from(Period.ofMonths(2)));
    }
}
