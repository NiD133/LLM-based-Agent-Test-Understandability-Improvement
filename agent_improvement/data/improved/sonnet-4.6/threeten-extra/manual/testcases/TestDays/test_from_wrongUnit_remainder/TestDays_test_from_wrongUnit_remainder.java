package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#from(java.time.temporal.TemporalAmount)} throws
 * {@link DateTimeException} when the supplied temporal amount cannot be
 * converted to a whole number of days (i.e. it leaves a remainder).
 *
 * <p>A {@link Duration} of 3 hours cannot be expressed as an exact integer
 * number of days, so {@code Days.from} must reject it.
 */
public class TestDays_test_from_wrongUnit_remainder {

    @Test
    public void test_from_wrongUnit_remainder() {
        // 3 hours cannot be converted to a whole number of days → remainder exists
        Duration threeHours = Duration.ofHours(3);
        assertThrows(DateTimeException.class, () -> Days.from(threeHours));
    }
}
