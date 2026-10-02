package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_null {

    @Test
    public void test_plus_TemporalAmount_null() {
        // Any Hours instance should reject null; use a non-zero value to ensure
        // the factory path doesn't short-circuit before the null-check in plus().
        Hours anyHours = Hours.of(Integer.MIN_VALUE + 1);

        assertThrows(NullPointerException.class, () -> anyHours.plus(null));
    }
}
