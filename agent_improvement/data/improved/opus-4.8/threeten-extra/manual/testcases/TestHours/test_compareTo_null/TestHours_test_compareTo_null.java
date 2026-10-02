package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_compareTo_null {

    /**
     * Comparing an {@code Hours} amount against {@code null} must throw a
     * {@link NullPointerException}.
     */
    @Test
    public void test_compareTo_null() {
        Hours fiveHours = Hours.of(5);

        assertThrows(NullPointerException.class, () -> fiveHours.compareTo(null));
    }
}
