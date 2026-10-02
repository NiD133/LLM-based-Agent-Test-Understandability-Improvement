package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#from(TemporalAmount)} rejects a {@code null} argument.
 */
public class TestWeeks_test_from_null {

    @Test
    public void from_nullTemporalAmount_throwsNullPointerException() {
        TemporalAmount nullAmount = null;

        assertThrows(NullPointerException.class, () -> Weeks.from(nullAmount));
    }
}
