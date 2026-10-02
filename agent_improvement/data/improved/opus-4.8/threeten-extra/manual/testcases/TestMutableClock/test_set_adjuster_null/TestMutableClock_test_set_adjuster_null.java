package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAdjuster;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#set(TemporalAdjuster)} rejects a null
 * adjuster by throwing a {@link NullPointerException}.
 */
public class TestMutableClock_test_set_adjuster_null {

    @Test
    public void set_withNullAdjuster_throwsNullPointerException() {
        MutableClock clock = MutableClock.epochUTC();

        // The cast disambiguates the overloaded set(...) methods, selecting
        // set(TemporalAdjuster) just as the original null literal did.
        TemporalAdjuster nullAdjuster = null;

        assertThrows(
                NullPointerException.class,
                () -> clock.set(nullAdjuster));
    }
}
