package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#withZone(java.time.ZoneId)} rejects a null
 * time-zone argument.
 */
public class TestMutableClock_test_withZone_null {

    @Test
    public void withZone_nullZone_throwsNullPointerException() {
        MutableClock clock = MutableClock.epochUTC();

        // The time-zone argument is mandatory, so passing null must be rejected.
        //noinspection DataFlowIssue - intentionally passing null to verify the guard clause
        assertThrows(NullPointerException.class, () -> clock.withZone(null));
    }
}
