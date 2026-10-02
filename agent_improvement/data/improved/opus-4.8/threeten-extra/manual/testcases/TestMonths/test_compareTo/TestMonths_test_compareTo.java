package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#compareTo(Months)}.
 * <p>
 * {@code compareTo} follows the {@link Comparable} contract: it returns
 * a negative, zero, or positive value when this amount is respectively
 * less than, equal to, or greater than the other amount.
 */
public class TestMonths_test_compareTo {

    @Test
    public void test_compareTo() {
        Months fiveMonths = Months.of(5);
        Months sixMonths = Months.of(6);

        // An amount compared with itself is equal.
        assertEquals(0, fiveMonths.compareTo(fiveMonths));
        // A smaller amount compared with a larger one is "less than".
        assertEquals(-1, fiveMonths.compareTo(sixMonths));
        // A larger amount compared with a smaller one is "greater than".
        assertEquals(1, sixMonths.compareTo(fiveMonths));
    }
}
