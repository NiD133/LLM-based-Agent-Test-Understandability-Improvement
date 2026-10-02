package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#plus(TemporalAmount)} rejects a {@code null} argument.
 */
public class TestWeeks_test_plus_TemporalAmount_null {

    @Test
    public void plus_nullTemporalAmount_throwsNullPointerException() {
        Weeks weeks = Weeks.of(Integer.MIN_VALUE + 1);

        assertThrows(NullPointerException.class, () -> weeks.plus((TemporalAmount) null));
    }
}
