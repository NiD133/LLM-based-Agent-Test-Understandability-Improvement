package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MutableClock#set(java.time.temporal.TemporalField, long)}
 * rejects a null field with a NullPointerException.
 */
public class TestMutableClock_test_set_fieldAndValue_nullField {

    @Test
    public void test_set_fieldAndValue_nullField() {
        MutableClock clock = MutableClock.epochUTC();
        // Passing null as the TemporalField must throw NullPointerException
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> clock.set(null, 0));
    }
}
