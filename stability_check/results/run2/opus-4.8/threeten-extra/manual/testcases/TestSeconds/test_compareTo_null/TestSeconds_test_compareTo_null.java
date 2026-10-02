package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#compareTo(Seconds)} rejects a null argument.
 */
public class TestSeconds_test_compareTo_null {

    @Test
    public void compareTo_withNullArgument_throwsNullPointerException() {
        Seconds fiveSeconds = Seconds.of(5);

        //noinspection DataFlowIssue - deliberately passing null to verify the null check
        assertThrows(NullPointerException.class, () -> fiveSeconds.compareTo(null));
    }
}
