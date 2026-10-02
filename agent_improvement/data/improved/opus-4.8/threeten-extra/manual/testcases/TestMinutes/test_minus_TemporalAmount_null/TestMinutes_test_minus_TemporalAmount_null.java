package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#minus(TemporalAmount)} rejects a null argument
 * by throwing a {@link NullPointerException}.
 */
public class TestMinutes_test_minus_TemporalAmount_null {

    @Test
    public void minus_nullTemporalAmount_throwsNullPointerException() {
        Minutes minutes = Minutes.of(Integer.MIN_VALUE + 1);

        assertThrows(NullPointerException.class, () -> minutes.minus((TemporalAmount) null));
    }
}
