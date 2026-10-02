package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#multipliedBy(int)} multiplies the week count
 * by a negative scalar, yielding a correctly negated result.
 */
public class TestWeeks_test_multipliedBy_negate {

    @Test
    public void multipliedBy_negativeScalar_negatesAndScalesAmount() {
        Weeks fiveWeeks = Weeks.of(5);

        Weeks result = fiveWeeks.multipliedBy(-3);

        assertEquals(Weeks.of(-15), result);
    }
}
