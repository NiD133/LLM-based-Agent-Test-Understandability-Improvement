package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#compareTo(Weeks)} rejects a null argument.
 */
public class TestWeeks_test_compareTo_null {

    @Test
    public void compareTo_nullArgument_throwsNullPointerException() {
        Weeks fiveWeeks = Weeks.of(5);

        //noinspection DataFlowIssue - intentionally passing null to verify the contract
        assertThrows(NullPointerException.class, () -> fiveWeeks.compareTo(null));
    }
}
