package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#plus(java.time.temporal.TemporalAmount)} rejects a
 * {@code null} amount by throwing a {@link NullPointerException}.
 */
public class TestYears_test_plus_TemporalAmount_null {

    @Test
    public void plus_nullTemporalAmount_throwsNullPointerException() {
        Years amount = Years.of(Integer.MIN_VALUE + 1);

        assertThrows(NullPointerException.class, () -> amount.plus(null));
    }
}
