package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#compareTo(Minutes)} rejects a {@code null} argument.
 */
public class TestMinutes_test_compareTo_null {

    @Test
    public void compareTo_nullArgument_throwsNullPointerException() {
        Minutes fiveMinutes = Minutes.of(5);

        // compareTo must reject null rather than treating it as a valid amount.
        assertThrows(NullPointerException.class, () -> fiveMinutes.compareTo(null));
    }
}
