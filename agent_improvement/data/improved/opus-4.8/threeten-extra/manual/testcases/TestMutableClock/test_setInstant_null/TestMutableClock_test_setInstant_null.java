package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#setInstant(Instant)} rejects a {@code null}
 * argument by throwing a {@link NullPointerException}.
 */
public class TestMutableClock_test_setInstant_null {

    @Test
    public void setInstant_withNullInstant_throwsNullPointerException() {
        MutableClock clock = MutableClock.epochUTC();

        // Passing null as the new instant is not allowed.
        assertThrows(
                NullPointerException.class,
                () -> clock.setInstant(null));
    }
}
