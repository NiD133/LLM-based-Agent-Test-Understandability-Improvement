package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#compareTo(Months)} rejects a {@code null} argument.
 */
public class TestMonths_test_compareTo_null {

    @Test
    public void compareTo_nullArgument_throwsNullPointerException() {
        Months fiveMonths = Months.of(5);

        assertThrows(NullPointerException.class, () -> fiveMonths.compareTo(null));
    }
}
