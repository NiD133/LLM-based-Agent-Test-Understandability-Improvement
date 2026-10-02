package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#plus(java.time.temporal.TemporalAmount)} throws
 * {@link NullPointerException} when a null argument is supplied.
 */
public class TestMinutes_test_plus_TemporalAmount_null {

    @Test
    public void test_plus_TemporalAmount_null() {
        Minutes someMinutes = Minutes.of(Integer.MIN_VALUE + 1);

        // Passing null as a TemporalAmount must throw NullPointerException
        assertThrows(NullPointerException.class, () -> someMinutes.plus(null));
    }
}
