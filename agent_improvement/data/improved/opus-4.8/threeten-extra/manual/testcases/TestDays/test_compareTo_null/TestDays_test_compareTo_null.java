package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#compareTo(Days)} rejects a {@code null} argument.
 */
public class TestDays_test_compareTo_null {

    @Test
    public void compareTo_null_throwsNullPointerException() {
        Days fiveDays = Days.of(5);

        // compareTo must reject a null argument rather than returning a value.
        assertThrows(NullPointerException.class, () -> fiveDays.compareTo(null));
    }
}
