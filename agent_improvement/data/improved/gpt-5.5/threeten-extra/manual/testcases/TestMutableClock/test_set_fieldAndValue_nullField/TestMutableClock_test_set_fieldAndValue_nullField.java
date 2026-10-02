package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_set_fieldAndValue_nullField {

    @Test
    public void test_set_fieldAndValue_nullField() {
        MutableClock clock = MutableClock.epochUTC();

        assertThrows(
                NullPointerException.class,
                () -> clock.set(null, 0));
    }
}
