package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#compareTo(Seconds)} rejects a {@code null} argument
 * by throwing a {@link NullPointerException}.
 */
public class TestSeconds_test_compareTo_null {

    @Test
    public void compareTo_null_throwsNullPointerException() {
        Seconds fiveSeconds = Seconds.of(5);

        // Comparing against null is illegal and must fail fast.
        assertThrows(NullPointerException.class, () -> fiveSeconds.compareTo(null));
    }
}
