package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#add(TemporalAmount)} rejects a null amount.
 */
public class TestMutableClock_test_add_amountOnly_null {

    @Test
    public void add_withNullAmount_throwsNullPointerException() {
        MutableClock clock = MutableClock.epochUTC();
        TemporalAmount nullAmount = null;

        assertThrows(
                NullPointerException.class,
                () -> clock.add(nullAmount));
    }
}
