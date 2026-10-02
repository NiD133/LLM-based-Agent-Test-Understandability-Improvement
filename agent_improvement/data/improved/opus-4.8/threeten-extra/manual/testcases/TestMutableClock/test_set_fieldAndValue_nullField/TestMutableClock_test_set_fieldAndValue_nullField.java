package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#set(TemporalField, long)} rejects a null
 * field by throwing a {@link NullPointerException}.
 */
public class TestMutableClock_test_set_fieldAndValue_nullField {

    @Test
    public void set_withNullField_throwsNullPointerException() {
        MutableClock clock = MutableClock.epochUTC();
        TemporalField nullField = null;

        assertThrows(
                NullPointerException.class,
                () -> clock.set(nullField, 0));
    }
}
