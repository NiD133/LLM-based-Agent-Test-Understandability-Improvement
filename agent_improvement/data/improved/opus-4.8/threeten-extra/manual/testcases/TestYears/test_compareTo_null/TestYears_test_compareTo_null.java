package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#compareTo(Years)} rejects a {@code null} argument.
 */
public class TestYears_test_compareTo_null {

    @Test
    public void compareTo_withNull_throwsNullPointerException() {
        Years fiveYears = Years.of(5);

        assertThrows(NullPointerException.class, () -> fiveYears.compareTo(null));
    }
}
